package com.georgiopoulos.core.domain.utils.formater

interface DateFormatter {

    fun formatUnixTime(unixTime: Long, pattern: String = DEFAULT_PATTERN) : String

    companion object {
        const val DEFAULT_PATTERN = "dd/MM/yy HH:mm"
    }
}