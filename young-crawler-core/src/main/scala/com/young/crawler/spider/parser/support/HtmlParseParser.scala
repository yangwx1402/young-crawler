package com.young.crawler.spider.parser.support

import com.young.crawler.entity.{HttpPage, HttpResult}
import com.young.crawler.spider.parser.Parser

/**
 * Created by young.yang on 2016/8/28.
 */
private[crawler] class HtmlParseParser extends Parser {
  override def parse(html: HttpResult): HttpPage = {
    val page = new HttpPage
    page.setContent(html.content)
    page.setUrl(html.url)
    page
  }
}
