package net.manub.socialnetwork.domain

import net.manub.socialnetwork.UnitSpec
import org.joda.time.DateTimeUtils

class MessageSpec extends UnitSpec {

  after {
    DateTimeUtils.setCurrentMillisSystem()
  }

  "calling toString on a Message" should {

    "print the user name, the text of the message and how many seconds ago was posted" in {

      DateTimeUtils.setCurrentMillisFixed(0)
      val message = Message(User("Alice"), "Hello world!")

      DateTimeUtils.setCurrentMillisFixed(1000)

      message.toString should be ("Alice - Hello world! (1 second ago)")

    }
  }

}
