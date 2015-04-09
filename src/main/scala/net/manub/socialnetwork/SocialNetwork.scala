package net.manub.socialnetwork

import net.manub.socialnetwork.domain.{User, Message}

case class SocialNetwork private (messages: List[Message] = List.empty,
                                  followers: Map[User, List[User]] = Map.empty) {

  def write(message: Message): SocialNetwork =
    copy(messages = messages :+ message)

  def follows(follower: User, follows: User): SocialNetwork =
    copy(followers = followers + (follower -> List(follows)))

  def messagesFrom(user: User): List[Message] =
    messages.filter(_.from == user)

  def followedBy(user: User): List[User] =
    followers.getOrElse(user, List.empty)

}

object SocialNetwork {
  def create: SocialNetwork = new SocialNetwork
}
