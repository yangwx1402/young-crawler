package com.young.crawler.spider.indexer

import com.young.crawler.entity.{IndexResult, HttpPage}

/**
 * Created by dell on 2016/8/29.
 */
trait Indexer {


  def index(page:HttpPage):IndexResult
}

object IndexerConstants{
  val indexName = "page"
  val indexType = "html"
}
