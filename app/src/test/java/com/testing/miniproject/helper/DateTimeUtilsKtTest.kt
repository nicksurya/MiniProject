package com.testing.miniproject.helper

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*

class DateTimeUtilsKtTest {

    lateinit var sampleDate  : Date
    lateinit var sampleDateOnly  : Date
    lateinit var sampleTimeOnly  : Date
    lateinit var sampleTodayDate  : Date

    @Before
    fun setUp() {
        val calendar = Calendar.getInstance()
        calendar.set(2000, 11, 31) // month  is from 0-11
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 30)

        sampleDate = calendar.time  // 31 Dec 2000 20:30

        val calendarDateOnly = Calendar.getInstance()
        calendarDateOnly.set(2000, 11, 31) // month  is from 0-11
        calendarDateOnly.set(Calendar.HOUR_OF_DAY, 0)
        calendarDateOnly.set(Calendar.MINUTE, 0)
        calendarDateOnly.set(Calendar.SECOND, 0)
        calendarDateOnly.set(Calendar.MILLISECOND, 0)

        sampleDateOnly = calendarDateOnly.time // 31 Dec 2000 00:00:00:00 -> till milliseconds

        val calendarTimeOnly = Calendar.getInstance()
        calendarTimeOnly.set(1970, 0, 1) // month  is from 0-11
        calendarTimeOnly.set(Calendar.HOUR_OF_DAY, 20)
        calendarTimeOnly.set(Calendar.MINUTE, 30)
        calendarTimeOnly.set(Calendar.SECOND, 0)
        calendarTimeOnly.set(Calendar.MILLISECOND, 0)

        sampleTimeOnly = calendarTimeOnly.time // 1 Jan 1970 20:30:00:00

        val calenderSampelToday = Calendar.getInstance()
        calenderSampelToday.set(2021, 1, 1) // month  is from 0-11

        sampleTodayDate = calenderSampelToday.time // 1 Feb 2021
    }

    @Test
    fun parseDateToString() {
        assertEquals(parseDateToString(sampleDate), "31/12/2000")
        assertNotEquals(parseDateToString(sampleDate), "31-12-2000")
        assertNotEquals(parseDateToString(sampleDate), "31 Dec 2000")
    }

    @Test
    fun parseTimeToString() {
        assertEquals(parseTimeToString(sampleDate), "20:30")
        assertNotEquals(parseTimeToString(sampleDate), "20.30")
        assertNotEquals(parseTimeToString(sampleDate), "20:30 WIB")
    }

    @Test
    fun parseStringToDate() {
        assertEquals(parseStringToDate("31/12/2000"), sampleDateOnly)
        assertNotEquals(parseStringToDate("31/12/2000"), sampleDate)
    }

    @Test
    fun parseTimeStringToDate() {
        assertEquals(parseTimeStringToDate("20:30"), sampleTimeOnly)
    }

    @Test
    fun timeIntervalIsValid() {
        //when
        val startTime ="03:00"
        val endTime = "04:00"

        assertTrue(timeIntervalIsValid(startTime, endTime))
        assertFalse(timeIntervalIsValid(endTime, startTime))

    }
}