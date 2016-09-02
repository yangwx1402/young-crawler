package com.young.crawler.spider.task.support.actor

import akka.actor.Actor
import com.young.crawler.entity.HttpPage
import com.young.crawler.spider.indexer.Indexer
import com.young.crawler.spider.task.IndexTask

/**
 * Created by dell on 2016/8/29.
 * 索引任务
 */
class IndexActorTask(indexer:Indexer) extends Actor with IndexTask{
  override def receive: Receive = {
    case page:HttpPage=>
      indexer.index(page)
  }
}
