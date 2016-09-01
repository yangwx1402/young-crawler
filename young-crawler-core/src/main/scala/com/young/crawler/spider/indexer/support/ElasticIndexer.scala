package com.young.crawler.spider.indexer.support

import java.net.InetAddress

import com.young.crawler.entity.{PageIndexEntity, IndexResult, HttpPage}
import com.young.crawler.spider.indexer.{IndexerConstants, Indexer}
import com.young.crawler.utils.{JsonUtil, MD5Util}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

/**
 * Created by dell on 2016/8/29.
 */
class ElasticIndexer extends Indexer{

  private val client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("115.29.47.216"), 9300))

  override def index(htmlpage: HttpPage): IndexResult = {
    println("index page -["+htmlpage+"]")
    val page = new PageIndexEntity
    page.setAuthor(htmlpage.getAuthor)
    page.setContent(htmlpage.getContent)
    page.setTitle(htmlpage.getTitle)
    page.setUrl(htmlpage.getUrl)
    page.setPublishTime(htmlpage.getPublishTime)
    page.setUpdateTime(htmlpage.getUpdateTime)
    client.prepareIndex(IndexerConstants.indexName,IndexerConstants.indexType).setId(MD5Util.md5(page.getUrl)).setSource(JsonUtil.toJson(page)).get()
    IndexResult(200)
  }
}