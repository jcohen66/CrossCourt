package actors

import actors.PrintActor.PrintMsg
import akka.actor.{ActorLogging, Actor}


object PrintActor {
  case class PrintMsg(msg: String)
}

class PrintActor extends Actor with ActorLogging {

  def receive = {
    case PrintMsg(s) => printToConsole(s)
    case _ => throw new IllegalStateException("Just fail already!")
  }

  def printToConsole(s: String): Unit = {

    println("\n***********************************")
    println(s)
    println("***********************************\n")

  }
}
