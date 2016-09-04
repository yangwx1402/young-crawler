package com.young.crawler.searcher.entity

import scala.beans.BeanProperty

/**
 * Created by young.yang on 2016/9/4.
 */
class PageIndexEntity {
  @BeanProperty
  var id:String = ""
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

}
