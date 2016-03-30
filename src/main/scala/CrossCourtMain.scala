import actors.deathwatch.Reaper.WatchMe
import actors._
import akka.actor.{PoisonPill, ActorSystem, Props}

object CrossCourtMain extends App {

  val system = ActorSystem("crosscourt-system")

  // Shutdown monitoring
  val reaper = system.actorOf(Props[ProductionReaper], "reaper")

  // Top-level actors
  val serviceActor = system.actorOf(Props[ServiceActor], "service-actor")
  val domainActor = system.actorOf(Props[DomainActor], "domain-actor")

  // Start to monitor top-level actors
  reaper ! WatchMe(domainActor)
  reaper ! WatchMe(serviceActor)



  Thread.sleep(5000)

  // Cleanup
  domainActor ! PoisonPill
  serviceActor ! PoisonPill


}
