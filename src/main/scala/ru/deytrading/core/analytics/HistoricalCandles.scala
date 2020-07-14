package ru.deytrading.core.analytics
import cats.effect.IO
import akka.util.ccompat.JavaConverters._

import ru.deytrading.core.analytics.pattern.{Absorption, Hammer, Harami}
import ru.tinkoff.invest.openapi.models.market.{Candle, HistoricalCandles, Instrument}

object HistoricalCandles extends Absorption with Hammer with Harami{
  def (k: HistoricalCandles).toAbsorptionUp(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= absorptionUp(k)(instrument)(f)(schedulerDB)
  def (k: HistoricalCandles).toHammerUp(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= hammerUp(k)(instrument)(f)(schedulerDB)
  def (k: HistoricalCandles).toHaramiUp(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= haramiUp(k)(instrument)(f)(schedulerDB)

  def (k: HistoricalCandles).toAbsorptionDown(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= absorptionDown(k)(instrument)(f)(schedulerDB)
  def (k: HistoricalCandles).toHammerDown(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= hammerDown(k)(instrument)(f)(schedulerDB)
  def (k: HistoricalCandles).toHaramiDown(f: String => IO[_])(instrument: Instrument) (schedulerDB: SchedulerService): IO[_]= haramiDown(k)(instrument)(f)(schedulerDB)
}