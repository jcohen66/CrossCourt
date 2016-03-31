package actors

import actors.PrintActor.PrintMsg
import akka.actor.{Actor, ActorLogging}
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class DomainActor extends Actor with ActorLogging {

  implicit val resolveTimeout = Timeout(5 seconds)
  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)


  def receive = {
    case _ =>
  }



  printActor ! PrintMsg("Hello from DomainActor")

  // printActor ! "failover"
}
