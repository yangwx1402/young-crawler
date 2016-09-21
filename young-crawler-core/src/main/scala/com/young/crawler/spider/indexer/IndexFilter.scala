package com.young.crawler.spider.indexer

import com.young.crawler.entity.HttpPage

/**
 * Created by dell on 2016/9/21.
 */
trait IndexFilter {
  def filter(htmlpage: HttpPage):Boolean
}
