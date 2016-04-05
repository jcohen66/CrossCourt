package actors

import actors.CalcActor.{Times2, Times2Response}
import actors.PrintActor.Print
import akka.actor.{Actor, ActorLogging}
import akka.http.Http
import akka.http.model.HttpMethods._
import akka.http.model._
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import dao.Database
import play.api.libs.json.Json
import play.modules.reactivemongo.json.BSONFormats
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class WebActor extends Actor with ActorLogging {
  // the actor system to use. Required for flowmaterializer and HTTP.
  // passed in implicit
  implicit val system = context.system
  implicit val materializer = FlowMaterializer()

  implicit val resolveTimeout = Timeout(5 seconds)
  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)

  // start the server on the specified interface and port.
  val serverBinding2 = Http().bind(interface = "localhost", port = 8091)
  serverBinding2.connections.foreach { connection =>
    connection.handleWith(Flow[HttpRequest].mapAsync(asyncHandler))
  }


  //  implicit val resolveTimeout = Timeout(5 seconds)
  val calcActor = Await.result(context.actorSelection(DomainActors.CalcActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)


  def receive = {
    case Times2Response(i) => printActor ! Print("Response: " + i)
    case _ =>
  }


  // With an async handler, we use futures. Threads aren't blocked.
  def asyncHandler(request: HttpRequest): Future[HttpResponse] = {
    // we match the request, and some simple path checking
    request match {
      // match specific path. Returns all the avaiable tickers
      case HttpRequest(GET, Uri.Path("/getAllTickers"), _, _, _) => {
        // make a db call, which returns a future.
        // use for comprehension to flatmap this into
        // a Future[HttpResponse]
        for {
          input <- Database.findAllTickers
        } yield {
          HttpResponse(entity = convertToString(input))
        }
      }

      case HttpRequest(GET, Uri.Path("/calc"), _, _, _) => {
        request.uri.query.get("num") match {
          case Some(n) =>
            Future[HttpResponse] {
              calcActor ! Times2(n.toInt)
              // printActor ! PrintMsg("got a calc request " + n)
              HttpResponse(status = StatusCodes.OK)
            }
        }

      }

      // match GET pat. Return a single ticker
      case HttpRequest(GET, Uri.Path("/get"), _, _, _) => {
        // next we match on the query paramter
        request.uri.query.get("ticker") match {
          // if we find the query parameter
          case Some(queryParameter) => {
            // query the database
            val ticker = Database.findTicker(queryParameter)
            // use a simple for comprehension, to make
            // working with futures easier.
            for {
              t <- ticker
            } yield {
              t match {
                case Some(bson) => HttpResponse(entity = convertToString(bson))
                case None => HttpResponse(status = StatusCodes.OK)
              }
            }
          }
          // if the query parameter isn't there
          case None => Future(HttpResponse(status = StatusCodes.OK))
        }
      }
      // Simple case that matches everything, just return a not found
      case HttpRequest(_, _, _, _, _) => {
        Future[HttpResponse] {
          HttpResponse(status = StatusCodes.NotFound)
        }
      }
    }
  }

  def convertToString(input: List[BSONDocument]): String = {
    input
      .map(f => convertToString(f))
      .mkString("[", ",", "]")
  }

  def convertToString(input: BSONDocument): String = {
    Json.stringify(BSONFormats.toJSON(input))
  }
}
