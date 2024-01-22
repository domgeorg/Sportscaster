package com.georgiopoulos.core.domain.utils.formater

import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TimeFormatterImpl @Inject constructor() : TimeFormatter {

    override fun formatTime(timeDifference: Long): String {
        val days = TimeUnit.SECONDS.toDays(timeDifference)
        val hours = TimeUnit.SECONDS.toHours(timeDifference) % 24
        val minutes = TimeUnit.SECONDS.toMinutes(timeDifference) % 60
        val seconds = timeDifference % 60

        val daysString = if (days > 0) {
            if (days == 1L) {
                "1 day "
            } else {
                "$days days "
            }
        } else {
            ""
        }

        return "$daysString%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}
