package com.young.crawler.exception

/**
 * Created by young.yang on 2016/8/31.
 * 自定义解析异常
 */
class ParseException(message:String,e:Throwable) extends Exception(message,e){

  def this(message:String) = this(message,new Exception(message))
}
