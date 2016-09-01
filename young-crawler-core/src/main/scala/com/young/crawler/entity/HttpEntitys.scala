package com.young.crawler.entity

import java.net.{DatagramSocket, DatagramPacket}

import scala.beans.BeanProperty

/**
 * Created by young.yang on 2016/8/28.
 */
case class HttpResult(status:Int,content:String,message:String,url:String){
  override def toString()="status="+status+",context length="+content.length+",url="+url
}

case class UrlInfo(url:String,parent:String)

case class IndexResult(status:Int)

case class Seed(url:String)

class HttpPage{
  @BeanProperty
  var url: String = ""
  @BeanProperty
  var title: String = ""
  @BeanProperty
  var html:String = ""
  @BeanProperty
  var content: String = ""
  @BeanProperty
  var publishTime: Long = 0
  @BeanProperty
  var updateTime: Long = 0
  @BeanProperty
  var author: String = ""
  @BeanProperty
  var keywords:String = ""
  @BeanProperty
  var desc:String = ""
  @BeanProperty
  var childLink:List[UrlInfo] = List()
  @BeanProperty
  var meta:Map[String,String] = Map()

  override def toString()="url="+url+",context length="+content.length

}

