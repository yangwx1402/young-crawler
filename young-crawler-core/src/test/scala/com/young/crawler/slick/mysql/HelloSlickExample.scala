package com.young.crawler.slick.mysql

import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random

/**
 * Created by dell on 2016/10/10.
 */
object HelloSlickExample {

  private val db = Database.forConfig("mysql")

  //private val db = Database.forURL(url = "jdbc:mysql://localhost:3306/slick", user = "root", password = "", driver = "com.mysql.jdbc.Driver")
  private val executor = AsyncExecutor("test1", numThreads = 10, queueSize = 1000)
  private val random = new Random

  private val user = TableQuery[User]

  private val timeout = Duration(1,"s")

  def randomCreateUser(num: Int): Unit = {
    val operation = DBIO.seq(user.schema.create)
    val result = db.run(operation)
    //Await.result(result,timeout)
    for (i <- 0 until num) {
      val rs = db.run(user +=(0, "user_" + i, 20 + random.nextInt(num), "city_" + i))
      Await.result(rs,timeout)
    }
  }

  def main(args: Array[String]) {
    HelloSlickExample.randomCreateUser(10)
  }
}
