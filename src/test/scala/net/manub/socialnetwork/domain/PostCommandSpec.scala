package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{SocialNetwork, UnitSpec}

class PostCommandSpec extends UnitSpec {

  val user = User("Alice")
  val message = Message(user, "hello world")

  "a posting command" should {

    "write a message in the social network" in {
      val command = new Post(message)

      val (socialNetWork, _) = command(SocialNetwork.create)

      socialNetWork.messages should contain only message
    }

    "not generate any output" in {
      val user = User("Alice")
      val message = Message(user, "hello world")

      val command = new Post(message)

      val (_, output) = command(SocialNetwork.create)

      output shouldBe 'empty
    }

    "add a message without removing existing messages" in {
      val user = User("Alice")
      val message = Message(user, "hello world")
      val anotherMessage = Message(user, "hello again")

      val command = new Post(anotherMessage)

      val (socialNetWork, _) = command(SocialNetwork.create.copy(messages = List(message)))

      socialNetWork.messages should contain theSameElementsAs List(message, anotherMessage)
    }
  }

}
