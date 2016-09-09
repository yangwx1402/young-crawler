package com.young.crawler.spider.fetcher.support

import com.young.crawler.config.{CrawlerConfig, CrawlerConfigContants}
import com.young.crawler.entity.{GenerateType, HttpResult, UrlInfo}
import com.young.crawler.exception.FetchException
import com.young.crawler.spider.fetcher.{Fetcher, FetcherCache}
import com.young.crawler.utils.MD5Util
import org.apache.commons.logging.LogFactory

/**
 * Created by young.yang on 2016/8/28.
 * 采用HttpClient实现的爬取器
 */
private[crawler] class HttpClientFetcher extends Fetcher {

  private val friendtime = CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_cralwer_fetcher_friendtime).toLong

  private val log = LogFactory.getLog(classOf[HttpClientFetcher])
  HttpWatch.WATCH_TYPE = HttpWatch.WATCH_TYPE_PROTOTYPE

  @throws[FetchException]
  override def fetchPage(url: UrlInfo): Option[HttpResult] = {
    val md5 = MD5Util.md5(url.url)
    log.info("fetcher cache size -" + FetcherCache.fetcherCache.size())
    if (url.urlType == GenerateType && FetcherCache.fetcherCache.contains(md5)) {
      log.info("url  -" + url + " is fetched ")
      return None
    } else {
      FetcherCache.fetcherCache.put(md5, 1)
    }
    try {
      val headers = HttpWatch.header(url.url)
      val encode = getEncode(headers)
      log.info("get " + url + " encode =" + encode)
      val start = System.currentTimeMillis()
      Thread.sleep(friendtime)
      val result = HttpWatch.get(url.url, encode)
      log.info("fetch url " + url + ", cost time -" + (System.currentTimeMillis() - start) + " content length -" + result.content.length)
      if (result.status == FETCH_SUCCESS) {
        Option(result)
      } else {
        throw new FetchException("fetch error code is -" + result.status + ",error url is " + url)
      }
    } catch {
      case e => throw new FetchException("fetch error message error url is " + url, e)
    }
  }
}
