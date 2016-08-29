package com.young.crawler.spider.indexer.support

import com.young.crawler.entity.{IndexResult, HttpPage}
import com.young.crawler.spider.indexer.Indexer

/**
 * Created by dell on 2016/8/29.
 */
class ElasticIndexer extends Indexer{



  override def index(page: HttpPage): IndexResult = {
    println("index page -["+page+"]")
    IndexResult(200)
  }
}
