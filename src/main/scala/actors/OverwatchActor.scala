package actors

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{OneForOneStrategy, Actor, ActorLogging}

class OverwatchActor extends Actor with ActorLogging {

  trait PrintActorProxy extends Actor
  trait AssemblyActorProxy extends Actor
  trait AuthenticationActorProxy extends Actor
  trait CacheActorProxy extends Actor
  trait CalcActorProxy extends Actor
  trait CalcWorkerRouterProxy extends Actor
  trait DaoActorProxy extends Actor
  trait DomainActorProxy extends Actor
  trait ServiceActorProxy extends Actor

  def receive = {
    case _ =>
  }

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case ae: ArithmeticException => Resume
    case _: Exception => Restart
  }

}
