package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{UnitSpec, SocialNetwork}

class ReadingCommandSpec extends UnitSpec {

  "a reading command" should {

    val alice = User("Alice")

    "not return any message when no messages have been posted" in {

      val command = Reading(alice, SocialNetwork.create)
      val (_, output) = command.execute()

      output shouldBe 'empty
    }

    "return a message posted by the user" in {

      val aMessageFromAlice = Message(alice, "hello world")
      val socialNetworkWithAMessageFromAlice = SocialNetwork.create.copy(messages = List(aMessageFromAlice))

      val command = Reading(alice, socialNetworkWithAMessageFromAlice)
      val (_, output) = command.execute()

      output should contain only aMessageFromAlice
    }

    "not return messages from people not followed by the user" in {

      val bob = User("Bob")
      val aMessageFromBob = Message(bob, "what's up?")
      val socialNetworkWithAMessageFromBob = SocialNetwork.create.copy(messages = List(aMessageFromBob))

      val command = Reading(alice, socialNetworkWithAMessageFromBob)
      val (_, output) = command.execute()

      output shouldBe 'empty
    }
  }

}
