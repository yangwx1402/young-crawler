package com.young.crawler.cache

import com.young.crawler.cache.support.{RedisCache, MapCache}

/**
 * Created by dell on 2016/9/9.
 */
object RedisCacheExample {

    def main(args: Array[String]) {
      val cache = new RedisCache[String, String]
      for(i<-0 to 10){
        cache.put("key_"+i,"value_"+i)
      }
      println(cache.contains("key_0"))
      println(cache.size())
      println(cache.get("key_12").isEmpty)
  }
}
