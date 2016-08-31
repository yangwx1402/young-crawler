package com.young.crawler.utils

import java.nio.charset.Charset

import com.google.common.hash.Hashing

/**
 * Created by dell on 2016/8/31.
 */
object MD5Util {

  def md5(line: String) = Hashing.md5().newHasher().putString(line, Charset.defaultCharset()).hash().toString

  def main(args: Array[String]) {
    println(MD5Util.md5("杨勇"))
    println(MD5Util.md5("123"))
  }
}
