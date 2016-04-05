package actors

import actors.CalcActor.Times2Response
import actors.PrintActor.Print
import actors.deathwatch.Reaper.WatchMe
import akka.actor.{Actor, ActorLogging, Props}
import akka.routing._
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class DomainActor extends Actor with ActorLogging {

  val domainReaper = context.actorOf(Props[ProductionReaper], "domain-reaper")

  implicit val resolveTimeout = Timeout(5 seconds)
  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)


  val calcRouterPoolActor = context.actorOf(RoundRobinPool(3).props(Props[CalcActor]), "round-robin-calc-pool")


  domainReaper ! WatchMe(calcRouterPoolActor)

  def receive = {
    case Times2Response(i) => printActor ! Print("Calculation response: " + i)
    case _ =>
  }


  printActor ! Print("Hello from DomainActor")

  // printActor ! "failover"


}
