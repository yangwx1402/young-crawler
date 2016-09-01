package com.young.crawler.boot

import java.io.File

import akka.actor.{Props, ActorSystem}
import com.young.crawler.entity.InitSeed
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.JsoupParser
import com.young.crawler.spider.task.support.actor.{FetchActorTask, ParseActorTask, IndexActorTask, InjectActorTask}

/**
 * Created by dell on 2016/8/29.
 */
object CrawlerBoot {

  private val system = ActorSystem("young-crawler")

  def start(seedPath:String,patitation:Int=1): Unit ={
    val initSeeds = InitSeed(seedPath)
    //每个角色的actor都可以通过组组成一组actor进行处理
    val indexerActor = system.actorOf(Props(new IndexActorTask(new ElasticIndexer)),"indexer")
    val parserActor = system.actorOf(Props(new ParseActorTask(new JsoupParser, indexerActor)),"parser")
    val fetcher = system.actorOf(Props(new FetchActorTask(new HttpClientFetcher, parserActor)),"fetcher")
    val injectActor = system.actorOf(Props(new InjectActorTask(fetcher)),"injector")
    injectActor!initSeeds
  }

  def stop(): Unit ={
    system.terminate()
  }

  def main(args: Array[String]) {
    val seedPath = CrawlerBoot.getClass.getResource("/").getPath+File.separator+"seeds.txt"
    CrawlerBoot.start(seedPath)
    //Thread.sleep(20000)
   // CrawlerBoot.stop()
  }
}
