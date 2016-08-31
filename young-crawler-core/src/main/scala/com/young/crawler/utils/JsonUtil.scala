package com.young.crawler.utils

import org.codehaus.jackson.map.ObjectMapper

/**
 * Created by dell on 2016/8/31.
 */
object JsonUtil {

  private val mapper = new ObjectMapper

  def toJson(obj:Any):String={
    mapper.writeValueAsString(obj)
  }
}
