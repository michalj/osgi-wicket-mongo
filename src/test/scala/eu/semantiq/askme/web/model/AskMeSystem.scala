package eu.semantiq.askme.web.model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory


class AskMeSystem(rootURL: String, driver: WebDriver) {
  
  def gotoMainPage = {
    driver.navigate().to(rootURL)
    PageFactory.initElements(driver, classOf[MainPage])
  }
  
  def close() { driver.close }

}