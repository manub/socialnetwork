package net.manub.socialnetwork.domain

import net.manub.socialnetwork.SocialNetwork

trait Command {
  def socialNetwork: SocialNetwork
  def execute(): (SocialNetwork, List[Message])
}

case class Posting(message: Message, socialNetwork: SocialNetwork) extends Command {
  def execute() = (socialNetwork.write(message), List.empty)
}

case class Reading(user: User, socialNetwork: SocialNetwork) extends Command {
  def execute() = (socialNetwork, socialNetwork.messagesFrom(user))
}

case class Following(follower: User, follows: User, socialNetwork: SocialNetwork) extends Command {
  def execute(): (SocialNetwork, List[Message]) = (socialNetwork.follows(follower, follows), List.empty)
}

