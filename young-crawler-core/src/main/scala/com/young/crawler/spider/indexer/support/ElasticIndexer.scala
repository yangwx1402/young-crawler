package com.young.crawler.spider.indexer.support

import java.net.InetAddress

import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity.{PageIndexEntity, IndexResult, HttpPage}
import com.young.crawler.exception.IndexException
import com.young.crawler.spider.indexer.{IndexerConstants, Indexer}
import com.young.crawler.utils.{JsonUtil, MD5Util}
import org.apache.commons.logging.LogFactory
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

/**
 * Created by dell on 2016/8/29.
 * ES索引器
 */
private[crawler] class ElasticIndexer extends Indexer{

  private val log = LogFactory.getLog(classOf[ElasticIndexer])

  private val host = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_host)

  private val port = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_indexer_es_port).toInt

  private val client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port))

  /**
   * 索引网页信息
   * @param htmlpage
   * @return
   */
  @throws[IndexException]
  override def indexPage(htmlpage: HttpPage): IndexResult = {
    log.info("index page url "+htmlpage.getUrl+" page info -["+htmlpage+"]")
    val page = new PageIndexEntity
    page.setAuthor(htmlpage.getAuthor)
   // page.setContent(htmlpage.getContent)
    page.setTitle(htmlpage.getTitle)
    page.setUrl(htmlpage.getUrl)
    page.setPublishTime(htmlpage.getPublishTime)
    page.setUpdateTime(htmlpage.getUpdateTime)
    page.setKeywords(htmlpage.getKeywords)
    page.setDesc(htmlpage.getDesc)
    client.prepareIndex(IndexerConstants.indexName,IndexerConstants.indexType).setId(MD5Util.md5(page.getUrl)).setSource(JsonUtil.toJson(page)).get()
    IndexResult(200)
  }
}
