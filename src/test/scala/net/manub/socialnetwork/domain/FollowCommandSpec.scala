package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{SocialNetwork, UnitSpec}

class FollowCommandSpec extends UnitSpec {

  "a following command" should {

    "add a new follower in the social network" in {

      val alice = User("Alice")
      val bob = User("Bob")
      val aMessageFromAlice = Message(alice, "hello world")

      val aSocialNetworkWithAMessageFromAlice = SocialNetwork.create.copy(messages = List(aMessageFromAlice))

      val command = new Follow(bob, alice)
      val (socialNetwork, _) = command(aSocialNetworkWithAMessageFromAlice)

      socialNetwork.followedBy(bob) should contain only alice
    }
  }

}
