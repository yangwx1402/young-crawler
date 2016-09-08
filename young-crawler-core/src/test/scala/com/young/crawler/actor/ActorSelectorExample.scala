package com.young.crawler.actor

import akka.actor.{Props, ActorSystem}
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}

/**
 * Created by young.yang on 2016/9/8.
 */
object ActorSelectorExample {

  def main(args: Array[String]) {
    val system = ActorSystem(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_appName))
    val actor = system.actorOf(Props[ActorExample],"print")
    actor!"test"
    println(actor)
    val actor2 = system.actorSelection("akka://young-crawler/user/print")
    actor2!"222"
  }
}
