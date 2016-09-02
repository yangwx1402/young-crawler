package com.young.crawler.spider.fetcher

import com.young.crawler.cache.support.MapCache

/**
 * Created by young.yang on 2016/9/2.
 * 网页缓存,用来爬取过程中的去重
 */
object FetcherCache {
   val fetcherCache = new MapCache[String,Byte]
}
