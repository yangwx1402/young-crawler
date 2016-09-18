package com.young.crawler.cluster

import akka.actor.Terminated
import akka.cluster.ClusterEvent.{MemberEvent, MemberRemoved, MemberUp, UnreachableMember}

/**
 * Created by dell on 2016/9/18.
 */
class EventCollector extends ClusterRoledWorker {


  override def receive: Receive = {
    case MemberUp(member) => log.info("member is up address is -{}", member.address)
    case UnreachableMember(member) => log.info("Member detected as Unreachable: {}", member)
    case MemberRemoved(member, previousStatus) => log.info("Member is Removed: {} after {}", member.address, previousStatus)
    case _: MemberEvent => log.info("receive a member event {}", _)
    case Registration =>
      context.watch(sender())
      works = works.:+(sender())
      log.info("Interceptor registered: " + sender)
      log.info("Registered interceptors: " + works.size)
    case Terminated(worker)=>
      //过滤掉已经停掉的worker
      works = works.filterNot(_==worker)
      //处理客户端模拟程序发送来的nginx日志
    case RawNginxRecord(sourceHost, line)=>

  }
}
