package com.young.crawler.spider.fetcher

import com.young.crawler.cache.Cache
import com.young.crawler.config.{CrawlerConfigContants, CrawlerConfig}

/**
 * Created by young.yang on 2016/9/2.
 * 网页缓存,用来爬取过程中的去重
 */
private[crawler] object FetcherCache {
   //val fetcherCache = new MapCache[String,Byte]
   val fetcherCache : Cache[String,Byte] = Class.forName(CrawlerConfig.getConfig.getString(CrawlerConfigContants.young_crawler_fetcher_cache_imp)).newInstance().asInstanceOf[(Cache[String,Byte])]
}
