package com.young.crawler.entity

/**
 * Created by young.yang on 2016/9/3.
 */
sealed trait Counter
case class FetchCounter(num:Int) extends Counter
case class FetchOk(num:Int) extends Counter
case class FetchError(num:Int) extends Counter
case class InjectCounter(num:Int) extends Counter
case class ParseCounter(num:Int) extends Counter
case class ParseChildUrlCounter(num:Int) extends Counter
case class IndexCounter(num:Int) extends Counter
case object PrintCounter extends Counter

