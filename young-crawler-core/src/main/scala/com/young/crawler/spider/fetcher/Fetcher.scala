package com.young.crawler.spider.fetcher

import com.young.crawler.cache.support.MapCache
import com.young.crawler.entity.HttpResult
import com.young.crawler.exception.FetchException
import org.apache.http.Header

/**
 * Created by young.yang on 2016/8/28.
 * 爬取接口
 */
trait Fetcher {

  private val CONTENT_TYPE = "Content-Type"

  private val DEFAULT_ENCODE = "utf-8"

  val FETCH_SUCCESS = 200

  val URL_NOT_FOUND = 404

  /**
   * 爬取网页入口
   */
  @throws[FetchException]
  def fetchPage(url:String):Option[HttpResult]

  /**
   * 根据网页header来探测网页编码
   * @param headers
   * @return
   */
  def getEncode(headers:Array[Header]):String={
    for(header<-headers){
      if(CONTENT_TYPE.equals(header.getName)){
         val temp = header.getValue.split("=")
        if(temp.length==2){
          return temp(1)
        }
      }
    }
    DEFAULT_ENCODE
  }
}
