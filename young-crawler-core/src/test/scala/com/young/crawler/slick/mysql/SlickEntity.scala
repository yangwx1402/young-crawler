package com.young.crawler.slick.mysql
import slick.driver.MySQLDriver.api._
import slick.lifted.ProvenShape

/**
 * Created by dell on 2016/10/10.
 */
class User(tag:Tag) extends Table[(Long,String,Int,String)](tag,"user") {

  def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

  def name = column[String]("username")

  def age = column[Int]("age")

  def city = column[String]("city")

  override def * : ProvenShape[(Long,String, Int,String)] = (id,name,age,city)
}
