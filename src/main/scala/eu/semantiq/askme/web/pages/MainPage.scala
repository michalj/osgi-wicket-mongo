package eu.semantiq.askme.web.pages

import org.apache.wicket.request.mapper.parameter.PageParameters
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.MongoConnection
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.list.ListItem
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject

import scala.collection.JavaConversions._
import org.apache.wicket.markup.html.basic.Label

case class Word(foreign: String)

class WordsDAO extends SalatDAO[Word, String](collection = MongoConnection()("askme")("words"))


class MainPage(parameters: PageParameters) extends BasePage(parameters) {
  val wordsDAO = new WordsDAO
  
  wordsDAO.insert(Word("hello word"))
  val words = wordsDAO.find(MongoDBObject()).toList
  println(words)
  add(new ListView[Word]("words", words) {
    override def populateItem(item: ListItem[Word]) {
      item.add(new Label("word", item.getModelObject().foreign))
    }
  })
}