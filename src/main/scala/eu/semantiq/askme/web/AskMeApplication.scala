package eu.semantiq.askme.web

import org.apache.wicket.model.IModel
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.request.Request
import org.apache.wicket.request.Response
import org.ops4j.pax.wicket.api.InjectorHolder
import org.ops4j.pax.wicket.api.PaxWicketBean
import eu.semantiq.askme.web.pages.MainPage

class OptionModel[T <: AnyRef](model: IModel[Option[T]]) extends IModel[T] {
  def setObject(v: T) { model.setObject(if (v == null) None else Some(v)) }
  def getObject = model.getObject match {
    case None => null.asInstanceOf[T]
    case Some(v) => v
  }
  def detach {}
}

class AskMeApplication extends WebApplication {
  def getHomePage = classOf[MainPage]
  override def init {
  }
}
