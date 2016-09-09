package com.young.crawler.cache

import com.young.crawler.cache.support.MapCache

/**
 * Created by dell on 2016/9/2.
 */
object MapCacheExample {
  def main(args: Array[String]) {
    val cache = new MapCache[String, String]
    for(i<-0 to 10){
      cache.put("key_"+i,"value_"+i)
    }
    println(cache.contains("key_0"))
    println(cache.keys())
    println(cache.size())
    println(cache.get("key_12").isEmpty)
  }
}
