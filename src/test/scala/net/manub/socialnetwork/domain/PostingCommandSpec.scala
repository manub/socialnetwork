package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{UnitSpec, SocialNetwork}
import org.scalatest.{ShouldMatchers, WordSpec}

class PostingCommandSpec extends UnitSpec {

  val user = User("Alice")
  val message = Message(user, "hello world")

  "a posting command" should {

    "write a message in the social network" in {
      val command = Posting(message, SocialNetwork.create)

      val (socialNetWork, _) = command.execute()

      socialNetWork.messages should contain only message
    }

    "not generate any output" in {
      val user = User("Alice")
      val message = Message(user, "hello world")

      val command = Posting(message, SocialNetwork.create)

      val (_, output) = command.execute()

      output shouldBe 'empty
    }

    "add a message without removing existing messages" in {
      val user = User("Alice")
      val message = Message(user, "hello world")
      val anotherMessage = Message(user, "hello again")

      val command = Posting(anotherMessage, SocialNetwork.create.copy(messages = List(message)))

      val (socialNetWork, _) = command.execute()

      socialNetWork.messages should contain theSameElementsAs List(message, anotherMessage)
    }
  }

}
