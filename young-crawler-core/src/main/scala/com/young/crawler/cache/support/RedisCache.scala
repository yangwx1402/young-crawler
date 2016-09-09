package com.young.crawler.cache.support

import com.young.crawler.cache.Cache
import com.young.crawler.config.CrawlerConfig
import redis.clients.jedis.JedisPool

/**
 * Created by dell on 2016/9/2.
 * 采用Redis实现的缓存
 */
private[crawler] class RedisCache[KEY, VALUE] extends Cache[KEY, VALUE] {

  private val JEDIS_HOST = CrawlerConfig.getConfig.getString("young.crawler.fetcher.cache.redis.host")

  private val JEDIS_PORT = CrawlerConfig.getConfig.getString("young.crawler.fetcher.cache.redis.port").toInt

  private val JEDIS_PASS = CrawlerConfig.getConfig.getString("young.crawler.fetcher.cache.redis.password")

  private val expire = CrawlerConfig.getConfig.getString("young.crawler.fetcher.cache.timeout").toInt

  private val jedisPool = new JedisPool(JEDIS_HOST, JEDIS_PORT)

  override def contains(key: KEY): Boolean = {
    val jedis = jedisPool.getResource
    val bool = jedis.exists(key.toString)
    jedis.close()
    bool
  }

  override def get(key: KEY): Option[VALUE] = {
    val jedis = jedisPool.getResource
    val result = Option(jedis.get(key.toString).asInstanceOf[VALUE])
    jedis.close()
    result
  }

  override def put(key:KEY,value:VALUE): Unit = {
    val jedis = jedisPool.getResource
    jedis.setex(key.toString,expire, value.toString)
    jedis.close()
  }

  override def size(): Int = 0

  override def keys(): Set[KEY] = throw new Exception("unsupport operation")
}
