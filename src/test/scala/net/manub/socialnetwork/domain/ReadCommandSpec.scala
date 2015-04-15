package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{SocialNetwork, UnitSpec}

class ReadCommandSpec extends UnitSpec {

  "a reading command" should {

    val alice = User("Alice")

    "not return any message when no messages have been posted" in {

      val command = new Read(alice)
      val (_, output) = command(SocialNetwork.create)

      output shouldBe 'empty
    }

    "return a message posted by the user" in {

      val aMessageFromAlice = Message(alice, "hello world")
      val socialNetworkWithAMessageFromAlice = SocialNetwork.create.copy(messages = List(aMessageFromAlice))

      val command = new Read(alice)
      val (_, output) = command(socialNetworkWithAMessageFromAlice)

      output should contain only aMessageFromAlice
    }

    "return only messages posted by the user" in {

      val bob = User("Bob")
      val aMessageFromBob = Message(bob, "what's up?")

      val aMessageFromAlice = Message(alice, "hello world")
      val anotherMessageFromAlice = Message(alice, "it's me again")

      val socialNetworkWithAMessageFromBob = SocialNetwork.create.copy(
        messages = List(aMessageFromBob, aMessageFromAlice, anotherMessageFromAlice))

      val command = new Read(alice)
      val (_, output) = command(socialNetworkWithAMessageFromBob)

      output should contain only (aMessageFromAlice, anotherMessageFromAlice)
    }
  }

}
