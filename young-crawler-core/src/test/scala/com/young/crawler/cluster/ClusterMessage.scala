package com.young.crawler.cluster

/**
 * Created by dell on 2016/9/18.
 */
case object Registration extends Serializable

sealed trait Event extends Serializable

case class RawNginxRecord(sourceHost: String, line: String) extends Event

case class NginxRecord(sourceHost: String, eventCode: String, line: String) extends Event

case class FilteredRecord(sourceHost: String, eventCode: String, line: String, logDate: String, realIp: String) extends Event
