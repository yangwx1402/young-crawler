package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor, Props}
import akka.event.Logging
import com.young.crawler.entity.{UrlInfo, InitSeed, Seed}
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.{JsoupParser, HtmlParseParser}
import com.young.crawler.spider.task.InjectTask

import scala.io.Source

/**
 * Created by dell on 2016/8/29.
 * 抓取种子注入任务,将需要抓取的任务注入到该任务中
 */
class InjectActorTask(fetcher:ActorRef) extends Actor with InjectTask {
  private val log = Logging(context.system, this)
  override def receive: Receive = {
    //初始化注入
    case init: InitSeed =>
      val seeds = initSeeds(init.seedPath, init.fileEncode)
      log.info("init seeds -"+seeds)
      seeds.map(seed => fetcher ! UrlInfo(seed.url,null))
      //子url注入
    case urls:List[UrlInfo]=>
      log.info("inject urls -"+urls)
      urls.filter(seed=>seed.url.startsWith("http")).map(seed=>fetcher!seed)
  }

  override def initSeeds(seedPath: String, fileEncode: String = "utf-8"): List[Seed] = Source.fromFile(seedPath, fileEncode).getLines().map(line => Seed(line)).toList

}
