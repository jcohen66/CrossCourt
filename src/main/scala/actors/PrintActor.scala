package actors

import actors.PrintActor.PrintMsg
import akka.actor.{ActorLogging, Actor}


object PrintActor {
  case class PrintMsg(msg: String)
}

class PrintActor extends Actor with ActorLogging {

  def receive = {
    case PrintMsg(s) => printToConsole(s)
    case _ =>
  }

  def printToConsole(s: String): Unit = {

    println("\n***********************************")
    println(s)
    println("***********************************\n")

  }
}
