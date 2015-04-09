package net.manub.socialnetwork.domain

import net.manub.socialnetwork.{SocialNetwork, UnitSpec}

class FollowingCommandSpec extends UnitSpec {

  "a following command" should {

    "add a new follower in the social network" in {

      val alice = User("Alice")
      val bob = User("Bob")
      val aMessageFromAlice = Message(alice, "hello world")

      val aSocialNetworkWithAMessageFromAlice = SocialNetwork.create.copy(messages = List(aMessageFromAlice))

      val command = Following(bob, alice, aSocialNetworkWithAMessageFromAlice)
      val (socialNetwork, _) = command.execute()

      socialNetwork.followedBy(bob) should contain only alice
    }
  }

}
