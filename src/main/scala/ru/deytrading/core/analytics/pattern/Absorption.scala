package ru.deytrading.core.analytics.pattern
import cats.effect._
import akka.util.ccompat.JavaConverters._
import ru.tinkoff.invest.openapi.models.market.{Candle, HistoricalCandles, Instrument}
import cats.effect.IO

trait Absorption {
  def absorptionUp(l: HistoricalCandles)(instrument:Instrument)(f: String => IO[_])(schedulerDB: SchedulerService): IO[_] =
    for {
      k  <- IO { l.candles.asScala.toList }
      q2 = k(k.size - 2)
      q1 = k.last
      _  = if (q1.isAbsorptionUp(q2))
        f(instrument.toStringTelegramUp).runAsyncAndForget(schedulerDB)
    } yield ()

  def absorptionDown(l: HistoricalCandles)(instrument:Instrument)(f: String => IO[_])(schedulerDB: SchedulerService): IO[_] =
    for {
      k  <- IO { l.candles.asScala.toList }
      q2 = k(k.size - 2)
      q1 = k.last
      _  = if (q1.isAbsorptionDown(q2))
        f(instrument.toStringTelegramDown).runAsyncAndForget(schedulerDB)
    } yield ()
}
