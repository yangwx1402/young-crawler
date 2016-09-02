package com.young.crawler.cache.support

import com.young.crawler.cache.Cache

/**
 * Created by dell on 2016/9/2.
 */
class RedisCache[KEY,VALUE] extends Cache[KEY,VALUE]{
  override def contains(key: KEY): Boolean = ???

  override def get(key: KEY): Option[VALUE] = ???

  override def put(key: KEY, value: VALUE): Unit = ???

  override def size(): Int = ???

  override def keys(): Set[KEY] = ???
}
