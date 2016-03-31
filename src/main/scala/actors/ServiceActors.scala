package actors

import akka.util.Timeout
import scala.concurrent.duration._

object ServiceActors {
  val resolveTimeout = Timeout(5 seconds)

  val ServiceBasePath = "akka://crosscourt-system/user/service-actor/"

  val PrintActorPath = ServiceBasePath + "print-actor"
  val AuthenticatorPath = ServiceBasePath + "crosscourt-authenticator-actor"
  val WebActorPath = ServiceBasePath + "web-actor"
}
