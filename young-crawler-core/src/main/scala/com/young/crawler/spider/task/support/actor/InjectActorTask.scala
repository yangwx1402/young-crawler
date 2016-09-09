package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor, Props}
import akka.event.Logging
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity._
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.{JsoupParser, HtmlParseParser}
import com.young.crawler.spider.task.InjectTask

import scala.io.Source

/**
 * Created by dell on 2016/8/29.
 * 抓取种子注入任务,将需要抓取的任务注入到该任务中
 */
private[crawler] class InjectActorTask(fetcher: ActorRef) extends Actor with InjectTask {
  private val log = Logging(context.system, this)

  private val countActor = context.system.actorSelection("akka://" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName) + "/user/" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))


  override def receive: Receive = {
    //初始化注入
    case init: InitSeed =>
      val seeds = initSeeds(init.seedPath, init.fileEncode)
      log.info("init seeds -" + seeds)
      seeds.map(seed => {
        fetcher ! UrlInfo(seed.url, null,SeedType,0)
        countActor ! InjectCounter(1)
      })
    //子url注入
    case urls: List[UrlInfo] =>
      log.info("inject urls -" + urls)
      urls.filter(seed => seed.url.startsWith("http")).map(seed => {
        fetcher ! seed
        countActor ! InjectCounter(1)
      }
      )
  }

  override def initSeeds(seedPath: String, fileEncode: String = "utf-8"): List[Seed] = {
    log.info("seedpath = ["+seedPath+"] encoding = ["+fileEncode+"]")
    if (seedPath == null || seedPath.trim.equals("") || seedPath.startsWith("classpath:")) {
      val temp = seedPath.split(":")
      log.info("classpath seedpath = ["+temp(1)+"]")
      Source.fromInputStream(classOf[InjectTask].getResourceAsStream(temp(1))).getLines().map(line => Seed(line)).toList
    } else
      Source.fromFile(seedPath, fileEncode).getLines().map(line => Seed(line)).toList
  }
}
