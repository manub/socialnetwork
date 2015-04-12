package net.manub.socialnetwork.domain

import org.joda.time.LocalDateTime

case class User(username: String)

case class Message(from: User, text: String) {
  val time = LocalDateTime.now()
}