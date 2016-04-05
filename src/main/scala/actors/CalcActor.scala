package actors

import actors.CalcActor.{Times2, Times2Response}
import akka.actor.{Props, Actor}

object CalcActor {
  trait CalcMessage
  case class Times2(i: Int) extends CalcMessage
  case class Times2Response(i: Int) extends CalcMessage

  def props = Props(classOf[CalcActor])
}

class CalcActor extends Actor {
  def receive = {
    case Times2(i) => sender ! Times2Response(i * 2)
    case _ =>
  }
}
