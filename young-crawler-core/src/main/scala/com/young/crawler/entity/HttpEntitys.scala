package com.young.crawler.entity

import java.net.{DatagramSocket, DatagramPacket}

import scala.beans.BeanProperty

/**
 * Created by young.yang on 2016/8/28.
 * 通过爬取回来的http原始页面
 */
case class HttpResult(status:Int,content:String,message:String,url:String){
  override def toString()="status="+status+",context length="+content.length+",url="+url
}

/**
 * 爬取url类
 * @param url  url
 * @param parent  父url
 */
case class UrlInfo(url:String,parent:String){
  override def toString()=url+"\n"
}

/**
 * 索引结果
 * @param status
 */
case class IndexResult(status:Int)

/**
 * 种子类
 * @param url  种子url
 */
case class Seed(url:String){
  override def toString() = url+"\n"
}

/**
 * 解析出来的HTTP网页信息
 */
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

