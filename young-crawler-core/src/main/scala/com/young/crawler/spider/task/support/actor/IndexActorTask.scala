package com.young.crawler.spider.task.support.actor

import akka.actor.Actor
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity.{IndexCounter, HttpPage}
import com.young.crawler.spider.indexer.Indexer
import com.young.crawler.spider.task.IndexTask

/**
 * Created by dell on 2016/8/29.
 * 索引任务
 */
class IndexActorTask(indexer: Indexer) extends Actor with IndexTask {

  private val countActor = context.system.actorSelection("akka://" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName) + "/user/" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))


  context.system.actorSelection("")

  override def receive: Receive = {
    case page: HttpPage =>
      indexer.index(page)
      countActor ! IndexCounter(1)
  }
}
