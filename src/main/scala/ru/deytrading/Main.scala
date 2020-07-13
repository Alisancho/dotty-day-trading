package ru.deytrading

import java.util.logging.Logger

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp}
import ru.tinkoff.invest.openapi.OpenApi
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApiFactory

import scala.concurrent.ExecutionContextExecutor

object Main extends IOApp {
  implicit val system: ActorSystem                = ActorSystem()
  implicit val ec: ExecutionContextExecutor       = system.dispatcher
  implicit val materializer: Materializer         = ActorMaterializer()
  
  def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO.unit
  } yield ExitCode(1)


  private val apiTask: IO[OpenApi] = for {
    log <- IO {Logger.getLogger("Pooo")}
    api <- IO {new OkHttpOpenApiFactory(TOKEN, log).createOpenApiClient(ec)}
  } yield api
}
