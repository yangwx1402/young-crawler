package controllers

import com.young.crawler.searcher.entity.PageIndexEntity
import com.young.crawler.searcher.service.SearchService
import com.young.crawler.searcher.utils.JsonUtils
import play.api.libs.json.{Writes, Json}
import play.api.mvc._

object Application extends Controller {

  def main = Action {
    Ok(views.html.main("young-crawler-searcher"))
  }


  def search(q: String) = Action {
      val list = SearchService.search(q)
      Ok(JsonUtils.toJson(list))
  }

}