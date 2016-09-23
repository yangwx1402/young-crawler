package com.young.crawler.spider.indexer

import java.util.{Locale, ResourceBundle}

import com.young.crawler.config.{CrawlerConfig, CrawlerConfigContants}
import com.young.crawler.entity.{HttpPage, IndexResult}
import com.young.crawler.exception.IndexException

/**
 * Created by dell on 2016/8/29.
 * 索引接口
 */
trait Indexer {

  private val indexFilter = Class.forName(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_filter_imp)).asInstanceOf[IndexFilter]
  /**
   * 文档索引
   * @param page
   * @return
   */

  @throws[IndexException]
  def indexPage(page: HttpPage): IndexResult

  @throws[IndexException]
  def index(page:HttpPage):IndexResult={
    if(!indexFilter.filter(page)){
      return IndexResult(-1)
    }
    indexPage(page)
  }
}

/**
 * ES中所有名称和类型
 */
object IndexerConstants {
  val indexName = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_name)
  val indexType = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_type)
}
