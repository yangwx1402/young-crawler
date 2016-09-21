package com.young.crawler.spider.fetcher

/**
 * Created by dell on 2016/9/21.
 */
trait FetchFilter {

  def filter(url:String):Boolean
}
