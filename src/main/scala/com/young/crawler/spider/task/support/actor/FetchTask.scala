package com.young.crawler.spider.task.support.actor

import akka.actor.Actor
import com.young.crawler.spider.fetcher.Fetcher
import com.young.crawler.spider.task.ParserTask

/**
 * Created by young.yang on 2016/8/28.
 */
class FetchTask(fetcher:Fetcher) extends Actor with FetchTask{
  override def receive: Receive = ???
}
