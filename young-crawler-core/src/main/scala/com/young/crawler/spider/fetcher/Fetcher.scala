package com.young.crawler.spider.fetcher

import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}
import com.young.crawler.entity.{GenerateType, UrlInfo, HttpResult}
import com.young.crawler.exception.FetchException
import com.young.crawler.utils.MD5Util
import org.apache.commons.logging.LogFactory
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

  private val fetchFilter = Class.forName(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_fetcher_filter_imp)).asInstanceOf[FetchFilter]

  val log = LogFactory.getLog(classOf[Fetcher])

  /**
   * 爬取网页入口
   */
  @throws[FetchException]
  def fetchPage(url: UrlInfo): Option[HttpResult]

  @throws[FetchException]
  def fetch(url: UrlInfo): Option[HttpResult] = {
    if (fetchFilter.filter(url.url)) {
      log.info("url is filter url -" + url.url)
      return None
    }
    val md5 = MD5Util.md5(url.url)
    log.info("fetcher cache size -" + FetcherCache.fetcherCache.size())
    if (url.urlType == GenerateType && FetcherCache.fetcherCache.contains(md5)) {
      log.info("url  -" + url + " is fetched ")
      return None
    } else {
      FetcherCache.fetcherCache.put(md5, 1)
    }
    fetchPage(url)
  }

  /**
   * 根据网页header来探测网页编码
   * @param headers
   * @return
   */
  def getEncode(headers: Array[Header]): String = {
    for (header <- headers) {
      if (CONTENT_TYPE.equals(header.getName)) {
        val temp = header.getValue.split("=")
        if (temp.length == 2) {
          return temp(1)
        }
      }
    }
    DEFAULT_ENCODE
  }
}
