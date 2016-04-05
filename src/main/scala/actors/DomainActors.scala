package actors

import akka.util.Timeout

import scala.concurrent.duration._

object DomainActors {
  val resolveTimeout = Timeout(5 seconds)

  val DomainBasePath = "akka://crosscourt-system/user/domain-actor/"

  val CalcActorPath = DomainBasePath + "round-robin-calc-pool"

}
