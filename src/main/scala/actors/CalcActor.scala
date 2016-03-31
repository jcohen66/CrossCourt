package actors

import actors.CalcActor.{Times2, Times2Response}
import akka.actor.Actor

object CalcActor {
  case class Times2(i: Int)
  case class Times2Response(i: Int)
}

class CalcActor extends Actor {
  def receive = {
    case Times2(i) => sender ! Times2Response(i * 2)
    case _ =>
  }
}
