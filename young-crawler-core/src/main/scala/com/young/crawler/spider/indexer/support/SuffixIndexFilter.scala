package com.young.crawler.spider.indexer.support

import com.young.crawler.entity.HttpPage
import com.young.crawler.spider.indexer.IndexFilter

/**
 * Created by dell on 2016/9/21.
 */
class SuffixIndexFilter extends IndexFilter{
  override def filter(htmlpage: HttpPage): Boolean = {
    val pointindex = htmlpage.url.lastIndexOf('.')
    val suffix = htmlpage.url.substring(pointindex+1,htmlpage.url.length)
    println(suffix)
    suffixs.contains(suffix)
  }
}
object SuffixIndexFilter{
  def main(args: Array[String]) {
    val test = new SuffixIndexFilter
    val htmlPage = new HttpPage()
    htmlPage.setUrl("http://mt.sohu.com/20160216/n437565830.shtml")
    println(test.filter(htmlPage))
  }
}
