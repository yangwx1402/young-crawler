package com.young.crawler.parser

import com.young.crawler.spider.fetcher.support.HttpClientFetcher
import org.jsoup.Jsoup

/**
 * Created by dell on 2016/9/1.
 */
object JsoupExample {

  def parserHtml(url:String): Unit ={
    val fetcher = new HttpClientFetcher
    val page = fetcher.fetchPage(url)
    val html = Jsoup.parse(page.content)
    println(html.charset())
    println(html.title())
    println(html.head().select("meta"))
  }

  def main(args: Array[String]) {
    val url = "http://www.163.com"
    JsoupExample.parserHtml(url)

  }
}
