package com.young.crawler.boot

import java.io.File

import akka.actor.{Props, ActorSystem}
import com.young.crawler.entity.InitSeed
import com.young.crawler.spider.task.support.actor.InjectActorTask

/**
 * Created by dell on 2016/8/29.
 */
object CrawlerBoot {
  def main(args: Array[String]) {
    val system = ActorSystem("young-crawler")
    val injectActor = system.actorOf(Props[InjectActorTask],"inject")
    val seedPath = CrawlerBoot.getClass.getResource("/").getPath+File.separator+"seeds.txt"
    val initSeeds = InitSeed(seedPath)
    injectActor!initSeeds
  }
}
