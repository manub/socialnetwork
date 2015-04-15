package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{SocialNetwork, UnitSpec}
import org.joda.time.DateTimeUtils

class WallCommandSpec extends UnitSpec {

  after {
    DateTimeUtils.setCurrentMillisSystem()
  }

  "a wall command" should {

    val alice = User("Alice")

    "return no messages when the user hasn't posted any messages and doesn't follow anybody" in {

      val command = new Wall(alice)

      val (_, output) = command(SocialNetwork.create)
      output shouldBe 'empty
    }

    "return the messages posted by the user" in {

      val aMessageFromAlice = Message(alice, "hello world")
      val command = new Wall(alice)

      val (_, output) = command(SocialNetwork.create.copy(messages = List(aMessageFromAlice)))
      output should contain only aMessageFromAlice
    }

    "not return messages posted by another user who is not followed" in {

      val bob = User("Bob")
      val aMessageFromBob = Message(bob, "yo what's up")
      val command = new Wall(alice)

      val (_, output) = command(SocialNetwork.create.copy(messages = List(aMessageFromBob)))
      output shouldBe 'empty
    }

    "return messages posted by followed users" in {

      val charlie = User("Charlie")
      val aMessageFromCharlie = Message(charlie, "hi there")
      val command = new Wall(alice)

      val (_, output) = command(SocialNetwork.create.copy(messages = List(aMessageFromCharlie), followers = Map(alice -> List(charlie))))
      output should contain only aMessageFromCharlie
    }

    "return messages in chronological order, from most recent" in {
      val charlie = User("Charlie")

      DateTimeUtils.setCurrentMillisFixed(1000)
      val aMessageFromAlice = Message(alice, "hello world")

      DateTimeUtils.setCurrentMillisFixed(2000)
      val aMessageFromCharlie = Message(charlie, "hi there")

      val command = new Wall(alice)

      val (_, output) = command(SocialNetwork.create.copy(messages = List(aMessageFromAlice, aMessageFromCharlie), followers = Map(alice -> List(charlie))))
      output should contain theSameElementsInOrderAs List(aMessageFromCharlie, aMessageFromAlice)

    }
  }
}
