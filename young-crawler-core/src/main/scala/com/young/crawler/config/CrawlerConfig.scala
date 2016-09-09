package com.young.crawler.config

import java.util.{Locale, ResourceBundle}

import org.apache.commons.logging.LogFactory

/**
 * Created by young.yang on 2016/9/3.
 */
private[crawler] object CrawlerConfig {

  private val log = LogFactory.getLog("com.young.crawler.config.CrawlerConfig")

  private val config = ResourceBundle.getBundle("crawler", Locale.getDefault)

  private var init_flag = true

  private def init(): Unit = {
    log.info("init crawler config start")
    val keys = config.keySet()
    val iterator = keys.iterator()
    while (iterator.hasNext) {
      val key = iterator.next()
      log.info("crawler config key = [" + key + "] value = [" + config.getString(key) + "]")
    }
    log.info("init crawler config end")
    init_flag = false
  }

  def getConfig = {
    if (init_flag) {
      init()
    }
    config
  }

}

