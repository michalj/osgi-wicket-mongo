package eu.semantiq.askme.web

import org.junit.Test

class SeeMainPageIT extends AbstractIT {

  feature("Main page") {
    scenario("Shows title") {
      withSystem {
        system => system.gotoMainPage.pageSource(_ should include ("AskMe")) 
      }
    }
  }

}