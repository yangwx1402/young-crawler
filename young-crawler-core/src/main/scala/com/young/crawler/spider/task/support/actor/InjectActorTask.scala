package com.young.crawler.spider.task.support.actor

import akka.actor.{ActorRef, Actor, Props}
import com.young.crawler.entity.{UrlInfo, InitSeed, Seed}
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.{JsoupParser, HtmlParseParser}
import com.young.crawler.spider.task.InjectTask

import scala.io.Source

/**
 * Created by dell on 2016/8/29.
 */
class InjectActorTask(fetcher:ActorRef) extends Actor with InjectTask {

  override def receive: Receive = {
    case init: InitSeed =>
      val seeds = initSeeds(init.seedPath, init.fileEncode)
      println(seeds)
      seeds.map(seed => fetcher ! UrlInfo(seed.url,null))
    case urls:List[UrlInfo]=>
      urls.map(seed=>fetcher!seed)
  }

  override def initSeeds(seedPath: String, fileEncode: String = "utf-8"): List[Seed] = Source.fromFile(seedPath, fileEncode).getLines().map(line => Seed(line)).toList

}
