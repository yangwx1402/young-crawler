package com.young.crawler.spider.fetcher.support

import com.young.crawler.entity.HttpResult
import com.young.crawler.exception.FetchException
import com.young.crawler.spider.fetcher.Fetcher
import com.young.crawler.utils.MD5Util
import org.apache.commons.logging.LogFactory

/**
 * Created by young.yang on 2016/8/28.
 */
class HttpClientFetcher extends Fetcher {
  private val log = LogFactory.getLog(classOf[HttpClientFetcher])
  @throws[FetchException]
  override def fetchPage(url: String): Option[HttpResult] = {
    val md5 = MD5Util.md5(url)
    if(fetcherCache.contains(md5)){
      return None
    }
    try {
      val headers = HttpWatch.header(url)
      val encode = getEncode(headers)
      log.info("get "+url+" encode ="+encode)
      val start = System.currentTimeMillis()
      val result = HttpWatch.get(url,encode)
      log.info("fetch url "+url+", cost time -"+(System.currentTimeMillis()-start))
      if (result.status == FETCH_SUCCESS) {
        fetcherCache.put(md5,1)
        Option(result)
      } else {
        throw new FetchException("fetch error code is -" + result.status+",error url is "+url)
      }
    } catch {
      case e => throw new FetchException("fetch error message error url is "+url, e)
    }
  }
}
