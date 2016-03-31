package actors

import actors.CalcActor.Times2Response
import actors.PrintActor.PrintMsg
import actors.deathwatch.Reaper.WatchMe
import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{OneForOneStrategy, Props, Actor, ActorLogging}
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class DomainActor extends Actor with ActorLogging {

  val domainReaper = context.actorOf(Props[ProductionReaper], "domain-reaper")

  implicit val resolveTimeout = Timeout(5 seconds)
  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)

  val calcActor = context.actorOf(Props[CalcActor], "calc-actor")

  domainReaper ! WatchMe(calcActor)

  def receive = {
    case Times2Response(i) => printActor ! PrintMsg("Calculation response: " + i)
    case _ =>
  }

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = true) {
    case ae: ArithmeticException => Resume
    case _: Exception => Restart
  }

  printActor ! PrintMsg("Hello from DomainActor")

  // printActor ! "failover"


}
