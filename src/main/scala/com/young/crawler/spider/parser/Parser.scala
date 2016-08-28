package com.young.crawler.spider.parser

import com.young.crawler.entity.HttpPage

/**
 * Created by young.yang on 2016/8/28.
 */
trait Parser {
  def parse(html:String):HttpPage
}
