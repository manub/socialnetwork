package net.manub.socialnetwork.domain

import net.manub.socialnetwork.SocialNetwork

case class Post private[domain](message: Message) extends Command {
  def apply(socialNetwork: SocialNetwork) = (socialNetwork.write(message), List.empty)
}

case class Read private[domain](user: User) extends Command {
  def apply(socialNetwork: SocialNetwork) = (socialNetwork, socialNetwork.messagesFrom(user))
}

case class Follow private[domain](follower: User, follows: User) extends Command {
  def apply(socialNetwork: SocialNetwork): (SocialNetwork, List[Message]) = (socialNetwork.follows(follower, follows), List.empty)
}

case class Wall private[domain](user: User) extends Command {
  def apply(socialNetwork: SocialNetwork): (SocialNetwork, List[Message]) = (socialNetwork, socialNetwork.wall(user))
}

object Factory extends CommandFactory {

  val post = "(.*) -> (.*)".r
  val follow = "(.*) follows (.*)".r
  val wall = "(.*) wall".r
  val read = "(.*)".r

  def apply(string: String): Command = string match {
    case post(user, message) => new Post(Message(User(user), message))
    case follow(follower, followed) => new Follow(User(follower), User(followed))
    case wall(user) => new Wall(User(user))
    case read(user) => new Read(User(user))
  }
}