package com.young.crawler.cluster

import akka.actor.{Props, ActorSystem, ActorRef, Actor}
import com.typesafe.config.ConfigFactory

/**
 * Created by dell on 2016/9/19.
 */
object LogMockActor{

  val ports = Seq("2751","2752", "2753")

  val actors = scala.collection.mutable.HashMap[String, ActorRef]()

  val events = Map(
    "2751" -> List(
      """10.10.2.72 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000lAOX&udid=25371384b2eb1a5dc5643e14626ecbd4&sessionid=25371384b2eb1a5dc5643e14626ecbd41440152875362&imsi=460002830862833&operator=1&network=1&timestamp=1440152954&action=14&eventcode=300039&page=200002& HTTP/1.0" "-" 204 0 "-" "Dalvik/1.6.0 (Linux; U; Android 4.4.4; R8207 Build/KTU84P)" "121.25.190.146"""",
      """10.10.2.8 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000VACO&udid=f6b0520cbc36fda6f63a72d91bf305c0&imsi=460012927613645&operator=2&network=1&timestamp=1440152956&action=1840&eventcode=100003&type=1&result=0& HTTP/1.0" "-" 204 0 "-" "Dalvik/1.6.0 (Linux; U; Android 4.4.2; GT-I9500 Build/KOT49H)" "61.175.219.69""""
    ),
    "2752" -> List(
      """10.10.2.72 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000gCo4&udid=636d127f4936109a22347b239a0ce73f&sessionid=636d127f4936109a22347b239a0ce73f1440150695096&imsi=460036010038180&operator=3&network=4&timestamp=1440152902&action=1566&eventcode=101010&playid=99d5a59f100cb778b64b5234a189e1f4&radioid=1100000048450&audioid=1000001535718&playtime=3& HTTP/1.0" "-" 204 0 "-" "Dalvik/1.6.0 (Linux; U; Android 4.4.4; R8205 Build/KTU84P)" "106.38.128.67"""",
      """10.10.2.72 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000kPSC&udid=2ee585cde388ac57c0e81f9a76f5b797&operator=0&network=1&timestamp=1440152968&action=6423&eventcode=100003&type=1&result=0& HTTP/1.0" "-" 204 0 "-" "Dalvik/v3.3.85 (Linux; U; Android L; P8 Build/KOT49H)" "202.103.133.112"""",
      """10.10.2.72 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000lABW&udid=face1161d739abacca913dcb82576e9d&sessionid=face1161d739abacca913dcb82576e9d1440151582673&operator=0&network=1&timestamp=1440152520&action=1911&eventcode=101010&playid=b07c241010f8691284c68186c42ab006&radioid=1100000000762&audioid=1000001751983&playtime=158& HTTP/1.0" "-" 204 0 "-" "Dalvik/1.6.0 (Linux; U; Android 4.1; H5 Build/JZO54K)" "221.232.36.250""""
    ),
    "2753" -> List(
      """10.10.2.8 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000krJw&udid=939488333889f18e2b406d2ece8f938a&sessionid=939488333889f18e2b406d2ece8f938a1440137301421&imsi=460028180045362&operator=1&network=1&timestamp=1440152947&action=1431&eventcode=300030&playid=e1fd5467085475dc4483d2795f112717&radioid=1100000001123&audioid=1000000094911&playtime=951992& HTTP/1.0" "-" 204 0 "-" "Dalvik/1.6.0 (Linux; U; Android 4.0.4; R813T Build/IMM76D)" "5.45.64.205"""",
      """10.10.2.72 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000kcpz&udid=cbc7bbb560914c374cb7a29eef8c2144&sessionid=cbc7bbb560914c374cb7a29eef8c21441440152816008&imsi=460008782944219&operator=1&network=1&timestamp=1440152873&action=360&eventcode=200003&page=200003&radioid=1100000046018& HTTP/1.0" "-" 204 0 "-" "Dalvik/v3.3.85 (Linux; U; Android 4.4.2; MX4S Build/KOT49H)" "119.128.106.232"""",
      """10.10.2.8 [21/Aug/2015:18:29:19 +0800] "GET /t.gif?installid=0000juRL&udid=3f9a5ffa69a5cd5f0754d2ba98c0aeb2&imsi=460023744091238&operator=1&network=1&timestamp=1440152957&action=78&eventcode=100003&type=1&result=0& HTTP/1.0" "-" 204 0 "-" "Dalvik/v3.3.85 (Linux; U; Android 4.4.3; S?MSUNG. Build/KOT49H)" "223.153.72.78""""
    )
  )

  ports.foreach { port =>
    // 创建一个Config对象
    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
    .withFallback(ConfigFactory.parseString("akka.cluster.roles = [collector]"))
    .withFallback(ConfigFactory.load())
    // 创建一个ActorSystem实例
    val system = ActorSystem("event-cluster-system", config)
    actors(port) = system.actorOf(Props[EventCollector], name = "collectingActor")
  }

  Thread.sleep(30000)
  for(i<-0 to 10){
    // 使用Akka的Scheduler，模拟定时发送日志记录消息
    ports.foreach { port =>
      events(port).foreach { line =>
        println("RAW: port=" + port + ", line=" + line)
        actors(port) ! RawNginxRecord("host.me:" + port, line)
      }
    }
    Thread.sleep(30000)
  }

  def main(args: Array[String]) {
    println(LogMockActor.ports)
  }
}
