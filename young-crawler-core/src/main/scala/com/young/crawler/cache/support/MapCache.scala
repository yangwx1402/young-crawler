package com.young.crawler.cache.support

import com.young.crawler.cache.Cache

import scala.collection.immutable.Nil
import scala.collection.mutable

/**
 * Created by dell on 2016/9/2.
 */
class MapCache[KEY,VALUE] extends Cache[KEY,VALUE]{

  private val map = new mutable.HashMap[KEY,VALUE]()

  override def contains(key: KEY): Boolean = map.contains(key)

  override def get(key: KEY): Option[VALUE] =map.get(key)

  override def put(key: KEY, value: VALUE): Unit = map.put(key,value)

  override def size(): Int = map.size

  override def keys(): scala.collection.Set[KEY] = map.keySet
}
