package com.georgiopoulos.core.domain.utils.formater

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DateFormatterImpl @Inject constructor() : DateFormatter {

    override fun formatUnixTime(unixTime: Long, pattern: String): String {
        val date = Date(unixTime * 1000L)
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }
}
