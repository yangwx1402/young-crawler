package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity.{FetchError, FetchOk, FetchCounter, UrlInfo}
import com.young.crawler.spider.fetcher.Fetcher
import com.young.crawler.spider.task.{FetchTask, ParserTask}

/**
 * Created by young.yang on 2016/8/28.
 * 网页抓取任务,采用Actor实现
 */
private[crawler] class FetchActorTask(fetcher: Fetcher, parserTask: ActorRef) extends Actor with FetchTask {

  private val countActor = context.system.actorSelection("akka://" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName) + "/user/" + CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_task_count_name))

  private val log = Logging(context.system, this)

  private var injector: ActorRef = null

  override def receive: Receive = {
    //处理抓取任务
    case page: UrlInfo =>
      injector = sender()
      val httpResult = fetcher.fetch(page)
      countActor ! FetchCounter(1)
      if (!httpResult.isEmpty) {
        parserTask ! httpResult.get
        log.info("FetcherTask send parserTask a httpResult [" + httpResult + "]")
        countActor ! FetchOk(1)
      } else {
        countActor ! FetchError(1)
      }
    //将解析完成的子url发送到注入任务继续抓取
    case urls: List[UrlInfo] => injector ! urls
  }
}
