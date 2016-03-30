package actors

import actors.AuthenticationActor._
import akka.actor.Actor

object AuthenticationActor {
  trait AuthenticationMessage
  case class Authenticate(u: String, p: String) extends AuthenticationMessage
  case object Granted  extends AuthenticationMessage
  case object Denied  extends AuthenticationMessage
  case object Unauthorized  extends AuthenticationMessage

}

class AuthenticationActor extends Actor {

  val users = Map("jcohen66" -> "abc123")

  def receive = {
    case Authenticate(u, p) => users.get(u) match {
      case Some(s) => sender ! Granted
      case None => sender ! Denied
    }
    case _ => sender ! Unauthorized
  }
}
