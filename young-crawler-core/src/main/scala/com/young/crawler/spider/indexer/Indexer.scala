package com.young.crawler.spider.indexer

import com.young.crawler.config.{CrawlerConfig, CrawlerConfigContants}
import com.young.crawler.entity.{HttpPage, IndexResult}

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
  def index(page: HttpPage): IndexResult
}

/**
 * ES中所有名称和类型
 */
object IndexerConstants {
  val indexName = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_name)
  val indexType = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_type)
}
