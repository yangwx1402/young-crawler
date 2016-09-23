package com.young.crawler.spider.fetcher

import java.util.{Locale, ResourceBundle}

/**
 * Created by dell on 2016/9/21.
 */
trait FetchFilter {

  def filter(url:String):Boolean

  val plugin_fetcher_config = ResourceBundle.getBundle("plugin/fetcher/filter",Locale.getDefault)
}
