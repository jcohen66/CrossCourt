package actors

import actors.PrintActor.PrintMsg
import actors.deathwatch.Reaper.WatchMe
import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}


object ServiceActor {
  val BasePath = "akka://user/service-actor/"
}

class ServiceActor extends Actor with ActorLogging {

  val serviceReaper = context.actorOf(Props[ProductionReaper], "service-reaper")

  val printActor = context.actorOf(Props[PrintActor], "print-actor")
  val daoActor = context.actorOf(Props(new DaoActor(printActor)), "dao-actor")
  val authenticatorActor = context.actorOf(Props[AuthenticationActor], "crosscourt-authenticator-actor")
  val userActor = context.actorOf(Props[UserActor], "user-actor")
  val webActor = context.actorOf(Props[WebActor], "web-actor")

  serviceReaper ! WatchMe(printActor)
  serviceReaper ! WatchMe(daoActor)
  serviceReaper ! WatchMe(authenticatorActor)
  serviceReaper ! WatchMe(userActor)
  serviceReaper ! WatchMe(webActor)

  printActor ! PrintMsg("This is a message")

  def receive = {
    case _ =>
  }

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = true) {
    case ae: ArithmeticException => Resume
    case _: Exception => Restart
  }
}
