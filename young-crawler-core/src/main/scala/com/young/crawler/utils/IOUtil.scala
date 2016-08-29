package com.young.crawler.utils

import java.io.{BufferedReader, InputStream, InputStreamReader}

/**
 * Created by young.yang on 2016/8/28.
 */
private [crawler] object IOUtil {

  def toString(inputStream:InputStream,encode:String):String={
    val bufferReader = new BufferedReader(new InputStreamReader(inputStream,encode))
    val buffer = new StringBuilder(1000)
    var line = bufferReader.readLine()
    while(line!=null){
       buffer.append(line+"\n")
      line = bufferReader.readLine()
    }
    return buffer.toString()
  }
}
