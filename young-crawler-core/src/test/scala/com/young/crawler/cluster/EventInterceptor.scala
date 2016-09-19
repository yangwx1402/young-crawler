package com.young.crawler.cluster

import akka.actor._
import akka.cluster.ClusterEvent._
import akka.cluster.Member
import akka.cluster.protobuf.msg.ClusterMessages.MemberStatus
import com.typesafe.config.ConfigFactory

/**
 * Created by dell on 2016/9/19.
 */
class EventInterceptor extends ClusterRoledWorker {

  @volatile var interceptedRecords: Int = 0

  val IP_PATTERN = "[^\\s]+\\s+\\[([^\\]]+)\\].+\"(\\d+\\.\\d+\\.\\d+\\.\\d+)\"".r

  val blackIpList = Array(
    "5.9.116.101", "103.42.176.138", "123.182.148.65", "5.45.64.205",
    "27.159.226.192", "76.164.228.218", "77.79.178.186", "104.200.31.117",
    "104.200.31.32", "104.200.31.238", "123.182.129.108", "220.161.98.39",
    "59.58.152.90", "117.26.221.236", "59.58.150.110", "123.180.229.156",
    "59.60.123.239", "117.26.222.6", "117.26.220.88", "59.60.124.227",
    "142.54.161.50", "59.58.148.52", "59.58.150.85", "202.105.90.142"
  ).toSet

  log.info("Black IP count: " + blackIpList.size)
  blackIpList.foreach(log.info(_))


  def getCollectorPath(member: Member): ActorPath = RootActorPath(member.address) / "user" / "collectingActor"


  /**
   * 检查collector发送的消息所对应的IP是否在黑名单列表中
   */

  private def checkRecord(eventCode: String, line: String): (Boolean, Map[String, String]) = {
    (true, Map())
  }


  override def receive: Receive = {
    case MemberUp(member) =>
      log.info("Member is up {}", member.address)
      //向数据收集actor进行注册,也就是说将该Interceptor注册到Collector内部的works列表中
      register(member, getCollectorPath)
    case state: CurrentClusterState =>
      // 如果加入Akka集群的成员节点是Up状态，并且是collector角色，则调用register向collector进行注册
      state.members.filter(_.status == MemberStatus.Up) foreach (register(_, getCollectorPath))
    case UnreachableMember(member) =>
      log.info("Member detected as Unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      log.info("Member is Removed: {} after {}", member.address, previousStatus)
    case event: MemberEvent => log.info("receive a member event {}", event)
    //处理注册过来的下游actor
    case Registration => {
      context watch sender
      works = works :+ sender
      log.info("Processor registered: " + sender)
      log.info("Registered processors: " + works.size)
    }
    case Terminated(processingActorRef) =>
      works = works.filterNot(_ == processingActorRef)
    case NginxRecord(sourceHost, eventCode, line) => {
      val (isIpInBlackList, data) = checkRecord(eventCode, line)
      if (!isIpInBlackList) {
        interceptedRecords += 1
        if (works.size > 0) {
          val processorIndex = (if (interceptedRecords < 0) 0 else interceptedRecords) % works.size
          works(processorIndex) ! FilteredRecord(sourceHost, eventCode, line, data.get("eventdate").get, data.get("realip").get)
          log.info("Details: processorIndex=" + processorIndex + ", processors=" + works.size)
        }
        log.info("Intercepted data: data=" + data)
      } else {
        log.info("Discarded: " + line)
      }

    }
  }
}

object EventInterceptor extends App {
  Seq("2851", "2852").foreach { port =>
    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
      .withFallback(ConfigFactory.parseString("akka.cluster.roles = [interceptor]"))
      .withFallback(ConfigFactory.load())
    val system = ActorSystem("event-cluster-system", config)
    val processingActor = system.actorOf(Props[EventInterceptor], name = "interceptingActor")
    system.log.info("Processing Actor: " + processingActor)
  }
}
