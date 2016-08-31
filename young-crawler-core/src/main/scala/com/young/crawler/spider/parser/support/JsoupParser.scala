package com.young.crawler.spider.parser.support

import com.young.crawler.entity.{HttpPage, HttpResult}
import com.young.crawler.spider.parser.Parser
import org.jsoup.Jsoup

/**
 * Created by young.yang on 2016/8/31.
 */
class JsoupParser extends Parser{
  override def parse(html: HttpResult): HttpPage = {
    val htmlPage = new HttpPage
    val document = Jsoup.parse(html.content)
    htmlPage.setTitle(document.title())
    htmlPage.setContent(document.text())
    htmlPage.setHtml(html.content)
    htmlPage.setPublishTime(System.currentTimeMillis())
    htmlPage.setUpdateTime(System.currentTimeMillis())
    htmlPage.setUrl(html.url)
    htmlPage
  }
}
