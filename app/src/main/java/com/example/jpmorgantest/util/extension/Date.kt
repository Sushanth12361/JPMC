package com.example.jpmorgantest.util.extension

import com.example.jpmorgantest.util.constants.DELAY_1000
import com.example.jpmorgantest.util.constants.FORMAT_HOUR
import com.example.jpmorgantest.util.constants.FORMAT_TIME_ZONE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.convertUnixTimestampToCentralTime(): String {
    val date = Date(this * DELAY_1000)

    val sdf = SimpleDateFormat(FORMAT_HOUR, Locale.US)

    sdf.timeZone = TimeZone.getTimeZone(FORMAT_TIME_ZONE)

    return sdf.format(date)
}
