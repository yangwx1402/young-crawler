package com.young.crawler.parser

import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import com.young.crawler.spider.parser.support.JsoupParser

/**
 * Created by dell on 2016/9/1.
 */
object JsoupExample {

  def parserHtml(url:String): Unit ={
    val fetcher = new HttpClientFetcher
    val parser = new JsoupParser
    val page = fetcher.fetchPage(url)
    println(page)
    val page1 = fetcher.fetchPage(url)
    println(page1)
    val result = parser.parse(page.get)
    println(result.keywords)
    println(result.desc)
    result.childLink.foreach(println _)
  }

  def main(args: Array[String]) {
    val url = "http://bj.fang.com/"
    JsoupExample.parserHtml(url)

  }
}
