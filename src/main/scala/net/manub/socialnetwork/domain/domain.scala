package net.manub.socialnetwork.domain

import org.joda.time.{Period, LocalDateTime}
import org.joda.time.format.PeriodFormatterBuilder

case class User(username: String)

case class Message(from: User, text: String) {
  val time = LocalDateTime.now()

  override def toString = s"${from.username} - $text (${Message.periodFormatter.print(new Period(time, LocalDateTime.now()))})"
}

object Message {
  private val periodFormatter = new PeriodFormatterBuilder()
    .appendSeconds()
    .appendSuffix(" second ago", " seconds ago")
    .printZeroNever()
    .toFormatter
}