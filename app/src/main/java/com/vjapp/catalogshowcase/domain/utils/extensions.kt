package com.vjapp.catalogshowcase.domain.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATETIME_ITALIAN_FORMAT: String = "dd/MM/yyyy HH:mm:ss"
const val DATE_ITALIAN_FORMAT: String = "dd/MM/yyyy"

fun Date.toFullString(pattern: String = DATETIME_ITALIAN_FORMAT): String {
    val format = SimpleDateFormat(pattern, Locale.ITALIAN)
    return format.format(this)
}

fun String.toDate(pattern: String = "dd/MM/yyyy"): Date {
    val format = SimpleDateFormat(pattern, Locale.ITALIAN)
    return format.parse(this)!!
}

