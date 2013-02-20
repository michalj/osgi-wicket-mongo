package eu.semantiq.askme.web

import org.scalatest.matchers.ShouldMatchers
import org.junit.Before
import org.junit.Before
import eu.semantiq.askme.web.model.AskMeSystem
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.FeatureSpec

abstract class AbstractIT extends FeatureSpec with ShouldMatchers {
  
  private var system: AskMeSystem =_
  
  def withSystem(body: AskMeSystem => Unit) {
    system = new AskMeSystem("http://localhost:8181/askme", new FirefoxDriver)
    body(system)
    system.close
  }
  
}