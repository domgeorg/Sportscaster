package com.georgiopoulos.core.domain.utils.formater

interface TimeFormatter {
    fun formatTime(timeDifference: Long): String
}