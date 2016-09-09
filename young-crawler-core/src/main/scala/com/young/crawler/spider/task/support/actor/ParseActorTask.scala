package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity.{ParseChildUrlCounter, ParseCounter, HttpPage, HttpResult}
import com.young.crawler.spider.parser.Parser
import com.young.crawler.spider.task.ParserTask

/**
 * Created by young.yang on 2016/8/28.
 * 解析任务
 */
private[crawler] class ParseActorTask(parser: Parser, indexTask: ActorRef) extends Actor with ParserTask {

  private val log = Logging(context.system, this)

  private val countActor = context.system.actorSelection("akka://" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName) + "/user/" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))

  private var fetcher: ActorRef = null

  override def receive: Receive = {
    case httpResult: HttpResult =>
      fetcher = sender()
      val page: HttpPage = parser.parse(httpResult)
      indexTask ! page
      countActor ! ParseCounter(1)
      log.info("ParserTask send IndexerTask a index request -[" + page + "]")
      val childLinks = page.getChildLink
      fetcher ! childLinks
      countActor ! ParseChildUrlCounter(childLinks.size)
  }
}
