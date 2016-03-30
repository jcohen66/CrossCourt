package actors

import actors.PrintActor.PrintMsg
import akka.actor.{ActorRef, Actor, ActorLogging}


class DaoActor(val pr: ActorRef) extends Actor with ActorLogging {

  val pa = context.actorSelection("user/service-actor/print-actor")


  pr ! PrintMsg("Hello from DaoActor")

  def receive = {
    case _ =>
  }
}
