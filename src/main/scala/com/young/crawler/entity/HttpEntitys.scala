package com.young.crawler.entity

import scala.beans.BeanProperty

/**
 * Created by young.yang on 2016/8/28.
 */
case class HttpResult(status:Int,content:String,message:String)

class HttpPage{
  @BeanProperty
  var url:String = ""
  @BeanProperty
  var content:String=""
  @BeanProperty
  var childLink:List[String] = List()
  @BeanProperty
  var meta:Map[String,String] = Map()
}
