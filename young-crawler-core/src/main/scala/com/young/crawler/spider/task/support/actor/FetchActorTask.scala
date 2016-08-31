package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.young.crawler.entity.UrlInfo
import com.young.crawler.spider.fetcher.Fetcher
import com.young.crawler.spider.task.{FetchTask, ParserTask}

/**
 * Created by young.yang on 2016/8/28.
 */
class FetchActorTask(fetcher:Fetcher,parserTask: ActorRef) extends Actor with FetchTask{

  private val log = Logging(context.system, this)

  private var injector:ActorRef = null

  override def receive: Receive = {
    case page:UrlInfo =>
      injector = sender()
      val httpResult = fetcher.fetchPage(page.url)
      parserTask!httpResult
      log.info("FetcherTask send parserTask a httpResult ["+httpResult+"]")
    case urls:List[UrlInfo]=>injector!urls
  }
}
