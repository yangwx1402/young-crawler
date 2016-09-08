package com.young.crawler.boot

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinPool
import akka.pattern.ask
import com.young.crawler.config.{CrawlerConfig, CrawlerConfigContants}
import com.young.crawler.entity.{PrintCounter, InitSeed}
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.JsoupParser
import com.young.crawler.spider.task.support.actor._
import org.apache.commons.logging.LogFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Created by dell on 2016/8/29.
 * 爬虫主函数
 */
object CrawlerBoot {

  private val system = ActorSystem(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName))

  private val log = LogFactory.getLog("young-cralwer")

  /**
   * 爬虫启动函数
   */
  def start(): Unit = {
    val initSeeds = InitSeed(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_seed_path))
    //每个角色的actor都可以通过组组成一组actor进行处理
    val parallel = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_parallel_int).toInt
    val indexerActor = system.actorOf(RoundRobinPool(parallel).props(Props(new IndexActorTask(new ElasticIndexer))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_index_name))
    log.info("create indexerActor name -["+indexerActor+"]")
    val parserActor = system.actorOf(RoundRobinPool(parallel).props(Props(new ParseActorTask(new JsoupParser, indexerActor))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_parse_name))
    log.info("create parserActor name -["+parserActor+"]")
    val fetcher = system.actorOf(RoundRobinPool(parallel).props(Props(new FetchActorTask(new HttpClientFetcher, parserActor))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_fetch_name))
    log.info("create fetcherActor name -["+fetcher+"]")
    val injectActor = system.actorOf(RoundRobinPool(parallel).props(Props(new InjectActorTask(fetcher))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_inject_name))
    log.info("create injectActor name -["+injectActor+"]")
    val countActor = system.actorOf(Props[CounterActorTask],CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))
    log.info("create countActor name -["+countActor+"]")
    injectActor ! initSeeds
  }

  /**
   * 停止爬虫程序
   */
  def stop(): Unit = {
    system.terminate()
  }

  def count():String = {
    val timeout = Duration(5,"s")
    val countActor = system.actorSelection("akka://" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName) + "/user/" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))
    val result = ask(countActor,PrintCounter)(timeout)
    Await.result(result,timeout).asInstanceOf[String]
  }

  def main(args: Array[String]) {
    CrawlerBoot.start()
//    Thread.sleep(5000)
//    println(CrawlerBoot.count())
//    CrawlerBoot.stop()
  }
}
