package com.young.crawler.boot

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinPool
import com.young.crawler.config.{CrawlerConfig, CrawlerConfigContants}
import com.young.crawler.entity.InitSeed
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.JsoupParser
import com.young.crawler.spider.task.support.actor.{FetchActorTask, IndexActorTask, InjectActorTask, ParseActorTask}

/**
 * Created by dell on 2016/8/29.
 * 爬虫主函数
 */
object CrawlerBoot {

  private val system = ActorSystem(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName))

  /**
   * 爬虫启动函数
   */
  def start(): Unit = {
    val initSeeds = InitSeed(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_seed_path))
    //每个角色的actor都可以通过组组成一组actor进行处理
    val parallel = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_parallel_int).toInt
    val indexerActor = system.actorOf(RoundRobinPool(parallel).props(Props(new IndexActorTask(new ElasticIndexer))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_index_name))
    val parserActor = system.actorOf(RoundRobinPool(parallel).props(Props(new ParseActorTask(new JsoupParser, indexerActor))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_parse_name))
    val fetcher = system.actorOf(RoundRobinPool(parallel).props(Props(new FetchActorTask(new HttpClientFetcher, parserActor))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_fetch_name))
    val injectActor = system.actorOf(RoundRobinPool(parallel).props(Props(new InjectActorTask(fetcher))), CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_inject_name))
    injectActor ! initSeeds
  }

  /**
   * 停止爬虫程序
   */
  def stop(): Unit = {
    system.terminate()
  }

  def main(args: Array[String]) {
    CrawlerBoot.start()
  }
}
