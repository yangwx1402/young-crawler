package com.young.crawler.spider.task.support.actor

import akka.actor.Actor
import com.young.crawler.spider.parser.Parser
import com.young.crawler.spider.task.ParserTask

/**
 * Created by young.yang on 2016/8/28.
 */
class ParseTask(parser:Parser) extends Actor with ParserTask{
  override def receive: Receive = ???
}
