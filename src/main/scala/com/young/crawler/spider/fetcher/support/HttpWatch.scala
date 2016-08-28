package com.young.crawler.spider.fetcher.support

import com.young.crawler.entity.HttpResult
import org.apache.commons.io.IOUtils
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{HttpGet, HttpUriRequest}
import org.apache.http.impl.client.HttpClients

/**
 * Created by young.yang on 2016/8/28.
 */
class HttpWatch(userAgent:String = "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2",timeout:Int = 3000,poolSize:Int=100) {

  private val defaultRequestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build()

  private val httpClient =  HttpClients.custom().setUserAgent(userAgent).setMaxConnTotal(poolSize)
    .setMaxConnPerRoute(poolSize).setDefaultRequestConfig(defaultRequestConfig).build();

  private def doGet(url:String,encode:String = "utf-8"):HttpResult={
     val get = new HttpGet(url)
     sendRequest(get,encode)
  }

  private def sendRequest(request:HttpUriRequest,encode:String): HttpResult ={
    val response = httpClient.execute(request)
    val statusCode = response.getStatusLine.getStatusCode
    val message = response.getStatusLine.getReasonPhrase
    val content = IOUtils.toString(response.getEntity.getContent,encode)
   // val content = IOUtil.toString(response.getEntity.getContent,encode)
    HttpResult(statusCode,content,message)
  }
}

object  HttpWatch{
  private val httpWatch = new HttpWatch
  def get(url:String,encode:String = "utf-8"):HttpResult = httpWatch.doGet(url,encode)
}
