package com.young.crawler.searcher.service

import java.net.InetAddress

import com.young.crawler.searcher.entity.PageIndexEntity
import org.elasticsearch.action.search.{SearchType, SearchResponse}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.index.query.QueryBuilders

/**
 * Created by young.yang on 2016/9/4.
 */
object SearchService {

  private val client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("115.29.47.216"), 9300))

  def search(key: String): Array[PageIndexEntity] = {
    val response = client.prepareSearch("page")
        .setTypes("html")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.fuzzyQuery("title",key))
        .setFrom(0).setSize(1000).setExplain(true)
      .execute()
      .actionGet()

    val hits = response.getHits()
    for(hit<-hits.getHits){
      println(hit.getSourceAsString)
    }

    val page = new PageIndexEntity
    page.setAuthor("young")
    page.setContent("搜索引擎测试")
    page.setId("1")
    page.setPublishTime(System.currentTimeMillis())
    page.setTitle("搜索引擎")
    page.setUrl("http://www.baidu.com")
    Array(page)
  }

  def main(args: Array[String]) {
    SearchService.search("新浪")
  }
}
