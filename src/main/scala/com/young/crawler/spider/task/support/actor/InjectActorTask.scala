package com.young.crawler.spider.task.support.actor

import akka.actor.{Actor, Props}
import com.young.crawler.entity.{UrlInfo, InitSeed, Seed}
import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.indexer.support.ElasticIndexer
import com.young.crawler.spider.parser.support.HtmlParseParser
import com.young.crawler.spider.task.InjectTask

import scala.io.Source

/**
 * Created by dell on 2016/8/29.
 */
class InjectActorTask extends Actor with InjectTask {

  val indexerActor = context.actorOf(Props(new IndexActorTask(new ElasticIndexer)))

  val parserActor = context.actorOf(Props(new ParseActorTask(new HtmlParseParser, indexerActor)))

  val fetcher = context.actorOf(Props(new FetchActorTask(new HttpClientFetcher, parserActor)))

  override def receive: Receive = {
    case init: InitSeed =>
      val seeds = initSeeds(init.seedPath, init.fileEncode)
      println(seeds)
      seeds.map(seed => fetcher ! UrlInfo(seed.url,null))
  }

  override def initSeeds(seedPath: String, fileEncode: String = "utf-8"): List[Seed] = Source.fromFile(seedPath, fileEncode).getLines().map(line => Seed(line)).toList

}
