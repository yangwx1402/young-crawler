package com.young.crawler.entity

import scala.beans.BeanProperty

/**
 * Created by dell on 2016/8/31.
 * 索引信息
 */
class PageIndexEntity {
  @BeanProperty
  var url: String = ""
  @BeanProperty
  var title: String = ""
  @BeanProperty
  var content: String = ""
  @BeanProperty
  var publishTime: Long = 0
  @BeanProperty
  var updateTime: Long = 0
  @BeanProperty
  var author: String = ""
  @BeanProperty
  var keywords:String =""
  @BeanProperty
  var desc:String = ""
}
