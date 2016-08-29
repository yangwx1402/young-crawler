package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.young.crawler.entity.{HttpPage, HttpResult}
import com.young.crawler.spider.parser.Parser
import com.young.crawler.spider.task.ParserTask

/**
 * Created by young.yang on 2016/8/28.
 */
class ParseActorTask(parser: Parser,indexTask:ActorRef) extends Actor with ParserTask {

  private val log = Logging(context.system, this)

  override def receive: Receive = {
    case httpResult: HttpResult =>
      val page:HttpPage = parser.parse(httpResult)
      indexTask!page
      log.info("ParserTask send IndexerTask a index request -["+page+"]")
  }
}
