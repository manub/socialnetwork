package net.manub.socialnetwork

import net.manub.socialnetwork.domain.{User, Message}
import org.joda.time.LocalDateTime

case class SocialNetwork private(messages: List[Message] = List.empty,
                                 followers: Map[User, List[User]] = Map.empty) {

  implicit def dateTimeDescOrdering: Ordering[LocalDateTime] = Ordering.fromLessThan(_ isAfter _)

  def write(message: Message): SocialNetwork =
    copy(messages = messages :+ message)

  def follows(follower: User, follows: User): SocialNetwork =
    copy(followers = followers + (follower -> List(follows)))

  def messagesFrom(user: User): List[Message] =
    messages.filter(_.from == user)

  def followedBy(user: User): List[User] =
    followers.getOrElse(user, List.empty)

  def wall(user: User): List[Message] = {
    val messages = messagesFrom(user) ++ messagesFromFollowedBy(user).getOrElse(List.empty)
    messages.sortBy(_.time)
  }

  private def messagesFromFollowedBy(user: User): Option[List[Message]] =
    followers.get(user).map(_ flatMap messagesFrom)
}

object SocialNetwork {
  def create: SocialNetwork = new SocialNetwork
}
