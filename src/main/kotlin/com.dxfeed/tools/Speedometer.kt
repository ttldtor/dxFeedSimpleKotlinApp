package com.dxfeed.tools

import com.devexperts.logging.Logging
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class Speedometer(period: Long) {
    private val logger = Logging.getLogging(Speedometer::class.java)
    private var counter: AtomicLong = AtomicLong(0)
    private var lastCount: AtomicLong = AtomicLong(0)
    private var countingTimer = timer("CountingTimer", false, 0L, period, action = {
        val eventsPerSecond = (counter.get() - lastCount.get()).toDouble() / period.toDouble() * 1000

        logger.info("Speedometer: ${String.format("%.3f", eventsPerSecond)} events/s")
        lastCount.set(counter.get())
    })

    fun addEvent() {
        counter.incrementAndGet()
    }

    fun addEvents(number: Long) {
        counter.addAndGet(number)
    }
}