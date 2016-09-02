package com.young.crawler.spider.parser.support

import com.young.crawler.entity.{UrlInfo, HttpPage, HttpResult}
import com.young.crawler.spider.parser.Parser
import org.jsoup.Jsoup
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer

/**
 * Created by young.yang on 2016/8/31.
 */
class JsoupParser extends Parser {

  private val KEYWORDS = "keywords"

  private val DESCRIPTION = "description"

  private def getMeta(key: String, meta: Elements): String = {
    for (i <- 0 until meta.size()) {
      val element = meta.get(i)
      if (key.equals(element.attr("name").toLowerCase)) {
        return element.attr("content")
      }
    }
    ""
  }

  private def parserUrls(urls: Elements): List[UrlInfo] = {
    val list = new ListBuffer[UrlInfo]()
    for (i <- 0 until urls.size()) {
      val element = urls.get(i)
      val url = element.attr("href")
      if (url.startsWith("http"))
        list.append(UrlInfo(url, ""))
    }
    list.toList
  }

  override def parse(html: HttpResult): HttpPage = {
    val htmlPage = new HttpPage
    val document = Jsoup.parse(html.content)
    val meta = document.select("meta")
    htmlPage.setTitle(document.title())
    htmlPage.setContent(document.text())
    // htmlPage.setHtml(html.content)
    htmlPage.setPublishTime(System.currentTimeMillis())
    htmlPage.setUpdateTime(System.currentTimeMillis())
    htmlPage.setUrl(html.url)
    htmlPage.setKeywords(getMeta(KEYWORDS, meta))
    htmlPage.setDesc(getMeta(DESCRIPTION, meta))
    htmlPage.setChildLink(parserUrls(document.body().select("a")))
    htmlPage
  }
}
