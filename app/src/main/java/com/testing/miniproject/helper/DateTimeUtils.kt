package com.testing.miniproject.helper

import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

fun parseDateToString(date : Date) : String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)
    return format.format(date)
}

fun parseTimeToString(time : Date) : String {
    val format = SimpleDateFormat("HH:mm", Locale.GERMAN)
    return format.format(time)
}


fun parseStringToDate(dateString : String?) : Date? {
    if (dateString.isNullOrEmpty()) return null

    val format = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)
    return try {
         format.parse(dateString)
    } catch(ex :Exception) {
        null
    }

}

fun parseTimeStringToDate(timeString : String?) : Date? {
    if (timeString.isNullOrEmpty()) return null

    val format = SimpleDateFormat("hh:mm", Locale.GERMAN)
    return try {
        format.parse(timeString)
    } catch(ex :Exception) {
        null
    }

}

fun getAge(birthDate : Date?) : Int {
    if (birthDate  == null) return 0

    val currentCalendar = Calendar.getInstance()
    val birthDateCalendar = Calendar.getInstance().apply { time = birthDate }

    var yearDiff = currentCalendar.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR)

    if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthDateCalendar.get(Calendar.DAY_OF_YEAR)) {
        yearDiff -= 1
    }

    return yearDiff
}

fun timeIntervalIsValid(startTimeString : String?,  endTimeString : String?) : Boolean {
    val startTime = parseTimeStringToDate(startTimeString)
    val endTime = parseTimeStringToDate(endTimeString)
    if (startTime == null || endTime == null) {
        return true
    } else {
        if (endTime.after(startTime))  {
            return true
        }
        return false
    }
}