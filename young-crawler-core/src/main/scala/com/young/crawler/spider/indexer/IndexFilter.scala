package com.young.crawler.spider.indexer

import java.util.{Locale, ResourceBundle}

import com.young.crawler.entity.HttpPage

/**
 * Created by dell on 2016/9/21.
 */
trait IndexFilter {


  private val plugin_index_config = ResourceBundle.getBundle("plugin/indexer/filter",Locale.getDefault())

  val suffixs = plugin_index_config.getString("young.crawler.indexer.filter.suffix").split(",").toSet


  def filter(htmlpage: HttpPage):Boolean
}
