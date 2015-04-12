package net.manub.socialnetwork.domain

import net.manub.socialnetwork.SocialNetwork

trait Command {
  def socialNetwork: SocialNetwork
  def execute(): (SocialNetwork, List[Message])
}

case class Posting private[domain](message: Message, socialNetwork: SocialNetwork) extends Command {
  def execute() = (socialNetwork.write(message), List.empty)
}

case class Reading private[domain](user: User, socialNetwork: SocialNetwork) extends Command {
  def execute() = (socialNetwork, socialNetwork.messagesFrom(user))
}

case class Following private[domain](follower: User, follows: User, socialNetwork: SocialNetwork) extends Command {
  def execute(): (SocialNetwork, List[Message]) = (socialNetwork.follows(follower, follows), List.empty)
}

case class Wall private[domain](user: User, socialNetwork: SocialNetwork) extends Command {
  def execute(): (SocialNetwork, List[Message]) = (socialNetwork, socialNetwork.wall(user))
}

