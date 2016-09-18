package com.young.crawler.cluster

import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef}
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberEvent, MemberUp, UnreachableMember}
import akka.cluster.{Cluster, Member}

/**
 * Created by dell on 2016/9/18.
 */
abstract class ClusterRoledWorker extends Actor with ActorLogging {

  //创建一个Akka Cluster
  val cluster = Cluster(context.system)
  //保存下游的worker节点
  var works = IndexedSeq.empty[ActorRef]

  // 订阅集群事件
  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberUp], classOf[UnreachableMember], classOf[MemberEvent])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  def register(member: Member, createPath: (Member) => ActorPath): Unit = {
    val actorPath = createPath(member)
    log.info("Actor Path - " + actorPath)
    val actor = context.actorSelection(actorPath)
    actor ! Registration
  }
}
