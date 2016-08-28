package com.young.crawler.spider.fetcher

import com.young.crawler.exception.FetchException

/**
 * Created by young.yang on 2016/8/28.
 */
trait Fetcher {

  val FETCH_SUCCESS = 200

  val URL_NOT_FOUND = 404

  @throws[FetchException]
  def fetchPage(url:String):String
}
