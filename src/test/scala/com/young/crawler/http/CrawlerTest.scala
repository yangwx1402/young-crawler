package com.young.crawler.http

import com.young.crawler.spider.fetcher.support.HttpWatch

/**
 * Created by young.yang on 2016/8/28.
 */
object CrawlerTest {

  def main(args: Array[String]) {
    val url = "http://www.sina.com.cn"
    val result = HttpWatch.get(url)
    println(result.content)
    println(result.status)
  }
}
