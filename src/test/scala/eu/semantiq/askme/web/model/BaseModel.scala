package eu.semantiq.askme.web.model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory


abstract class BaseModel(driver: WebDriver) {
  protected def getPageModel[P <: BaseModel](clazz: Class[P]): P = PageFactory.initElements(driver, clazz)
  def pageSource(predicate: String => Unit) {
    predicate(driver.getPageSource)
  }
}