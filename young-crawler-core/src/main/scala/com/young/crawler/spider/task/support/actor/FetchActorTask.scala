package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.young.crawler.entity.UrlInfo
import com.young.crawler.spider.fetcher.Fetcher
import com.young.crawler.spider.task.{FetchTask, ParserTask}

/**
 * Created by young.yang on 2016/8/28.
 * 网页抓取任务,采用Actor实现
 */
class FetchActorTask(fetcher: Fetcher, parserTask: ActorRef) extends Actor with FetchTask {

  private val log = Logging(context.system, this)

  private var injector: ActorRef = null

  override def receive: Receive = {
    //处理抓取任务
    case page: UrlInfo =>
      injector = sender()
      val httpResult = fetcher.fetchPage(page.url)
      if (!httpResult.isEmpty) {
        parserTask ! httpResult.get
        log.info("FetcherTask send parserTask a httpResult [" + httpResult + "]")
      }
      //将解析完成的子url发送到注入任务继续抓取
    case urls: List[UrlInfo] => injector ! urls
  }
}
