package com.helicoptera.nutrition.date

import java.util.Date
import java.util.Calendar
import java.util.concurrent.TimeUnit

object DateTimeProvider {

    fun getCurrentTime(): Date = Calendar.getInstance().time

    fun getMinutesToMidnight(): Long {
        val minutesPerDay = TimeUnit.DAYS.toMinutes(1)
        val hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong()
        val minutes = Calendar.getInstance().get(Calendar.MINUTE).toLong()
        val totalMinutes = TimeUnit.HOURS.toMinutes(hours) + minutes

        return minutesPerDay - totalMinutes
    }
}