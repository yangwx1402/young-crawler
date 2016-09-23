package com.young.crawler.spider.fetcher.support

import com.young.crawler.spider.fetcher.FetchFilter

/**
 * Created by dell on 2016/9/21.
 */
class DomainPrefixFetcherFilter extends FetchFilter{

  private val prefixSet = plugin_fetcher_config.getString("young.crawler.indexer.filter.prefix").split(",").toSet

  override def filter(url: String): Boolean = true
}
