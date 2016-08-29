package com.young.crawler.spider.task

import com.young.crawler.entity.Seed

/**
 * Created by young.yang on 2016/8/28.
 */
trait InjectTask {
  def initSeeds(seedPath:String,fileEncode:String="utf-8"): List[Seed]
}
