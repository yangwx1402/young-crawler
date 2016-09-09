package com.young.crawler.actor

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
 * Created by young.yang on 2016/9/8.
 */
class ActorExample extends Actor{
  override def receive: Receive = {
    case line:String=>println("receive a message "+line)
  }
}
