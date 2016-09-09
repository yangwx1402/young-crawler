package com.young.crawler.spider.parser

import com.young.crawler.entity.{HttpResult, HttpPage}

/**
 * Created by young.yang on 2016/8/28.
 * html页面解析接口
 */
trait Parser {
  def parse(html:HttpResult):HttpPage
}
