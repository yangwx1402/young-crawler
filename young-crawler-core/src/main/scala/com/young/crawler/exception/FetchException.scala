package com.young.crawler.exception

/**
 * Created by young.yang on 2016/8/28.
 * 自定义抓取异常
 */
class FetchException(message:String,e:Throwable) extends Exception(message,e){

  def this(message:String)=this(message,new Exception(message))

}
