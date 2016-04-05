package actors

import actors.AuthenticationActor.{Authenticate, AuthenticationMessage, Denied, Granted}
import actors.PrintActor.Print
import akka.actor.Actor
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class UserActor extends Actor {
  implicit val resolveTimeout = Timeout(5 seconds)
  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)
  val authenticateActor = Await.result(context.actorSelection(ServiceActors.AuthenticatorPath).resolveOne(), ServiceActors.resolveTimeout.duration)


  def receive = {
    case am: AuthenticationMessage => am match {
      case Granted => printActor ! Print("Access Granted")
      case Denied =>  printActor !  Print("Access Denied")
      case _ => printActor ! Print("Unauthorized access attempt")
    }
  }

  authenticateActor ! Authenticate("jcohen66", "")
}
