package com.young.crawler.spider.indexer

import com.young.crawler.entity.{IndexResult, HttpPage}

/**
 * Created by dell on 2016/8/29.
 * 索引接口
 */
trait Indexer {

  /**
   * 文档索引
   * @param page
   * @return
   */
  def index(page:HttpPage):IndexResult
}

/**
 * ES中所有名称和类型
 */
object IndexerConstants{
  val indexName = "page"
  val indexType = "html"
}
