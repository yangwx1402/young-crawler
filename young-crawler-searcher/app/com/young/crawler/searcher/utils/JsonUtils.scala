package com.young.crawler.searcher.utils

import org.codehaus.jackson.map.ObjectMapper

/**
 * Created by young.yang on 2016/9/4.
 */
object JsonUtils {

  private val mapper = new ObjectMapper

  def toJson(any:Any) = mapper.writeValueAsString(any)
}
