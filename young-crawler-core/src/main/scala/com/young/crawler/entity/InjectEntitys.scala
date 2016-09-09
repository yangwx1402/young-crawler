package com.young.crawler.entity

/**
 * Created by dell on 2016/8/29.
 * 初始化种子消息,用来传递给Inject Actor解析种子信息
 */
case class InitSeed(seedPath:String,fileEncode:String="utf-8")
