package ru.deytrading.core.analytics.pattern

import akka.util.ccompat.JavaConverters._
import ru.tinkoff.invest.openapi.models.market.{Candle, HistoricalCandles, Instrument}
import ru.deytrading.core.analytics.InstrumentCandles._
import cats.effect.IO

trait Hammer {
  def hammerUp(l: HistoricalCandles)(instrument:Instrument)(f: String => IO[_])(schedulerDB: SchedulerService): IO[_] =
    for {
      k <- IO {l.candles.asScala.toList}
      q4 = k(k.size - 4)
      q3 = k(k.size - 3)
      q2 = k(k.size - 2)
      q1 = k.last
      _  = if ((q2, q1).trendUp && (q4, q3, q2).trendDown && q2.isHammer)
        f(instrument.toStringTelegramUp).runAsyncAndForget(schedulerDB)
    } yield ()

  def hammerDown(l: HistoricalCandles)(instrument:Instrument)(f: String => IO[_])(schedulerDB: SchedulerService): IO[_] =
    for {
      k <- IO {l.candles.asScala.toList}
      q4 = k(k.size - 4)
      q3 = k(k.size - 3)
      q2 = k(k.size - 2)
      q1 = k.last
      _  = if ((q2, q1).trendDown && (q4, q3, q2).trendUp && q2.isHammer)
        f(instrument.toStringTelegramDown).runAsyncAndForget(schedulerDB)
    } yield ()
}
