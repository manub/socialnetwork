package net.manub.socialnetwork.domain

import net.manub.socialnetwork.UnitSpec

class CommandFactorySpec extends UnitSpec {

  "a command factory" when {

    """ Reading a string "username -> message" """ should {
      "create a new post command" in {
        Factory("Alice -> Hello world!") should be (new Post(Message(User("Alice"), "Hello world!")))
      }
    }

    """ Reading a string "username" """ should {
      "create a new read command" in {
        Factory("Alice") should be (new Read(User("Alice")))
      }
    }

    """ Reading a string "username follows user" """ should {
      "create a new follows command" in {
        Factory("Alice follows Bob") should be (new Follow(User("Alice"), User("Bob")))
      }
    }

    """ Reading a string "username wall" """ should {
      "create a new wall command" in {
        Factory("Alice wall") should be (new Wall(User("Alice")))
      }
    }
  }

}
