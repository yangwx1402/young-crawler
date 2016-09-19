package com.young.crawler.cluster

import akka.actor.Terminated
import akka.cluster.ClusterEvent.{MemberEvent, MemberRemoved, MemberUp, UnreachableMember}

/**
 * Created by dell on 2016/9/18.
 */
class EventCollector extends ClusterRoledWorker {

  @volatile var recordCounter: Int = 0

  override def receive: Receive = {
    case MemberUp(member) => log.info("member is up address is -{}", member.address)
    case UnreachableMember(member) => log.info("Member detected as Unreachable: {}", member)
    case MemberRemoved(member, previousStatus) => log.info("Member is Removed: {} after {}", member.address, previousStatus)
    case event: MemberEvent => log.info("receive a member event {}", event)
    case Registration =>
      context.watch(sender())
      works = works.:+(sender())
      log.info("Interceptor registered: " + sender)
      log.info("Registered interceptors: " + works.size)
    case Terminated(worker) =>
      //过滤掉已经停掉的worker
      works = works.filterNot(_ == worker)
    //处理客户端模拟程序发送来的nginx日志
    case RawNginxRecord(sourceHost, line) =>
      val eventCode = "eventcode=(\\d+)".r.findFirstIn(line).get
      log.info("Raw message: eventCode=" + eventCode + ", sourceHost=" + sourceHost + ", line=" + line)
      recordCounter += 1
      if (works.size > 0) {
        // 模拟Roudrobin方式，将日志记录消息发送给下游一组interceptor中的一个
        val interceptorIndex = (if (recordCounter < 0) 0 else recordCounter) % works.size
        works(interceptorIndex) ! NginxRecord(sourceHost, eventCode, line)
        log.info("Details: interceptorIndex=" + interceptorIndex + ", interceptors=" + works.size)
      }
  }
}
