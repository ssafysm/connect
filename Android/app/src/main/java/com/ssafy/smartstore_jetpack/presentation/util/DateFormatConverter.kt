package com.ssafy.smartstore_jetpack.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class DateFormatConverter {

    private val yesterdayTime = LocalDateTime.now().plusDays(-1)
    private val todayTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    private fun getDayOfWeek(): String {
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            1 -> return ApplicationClass.myContext().getString(R.string.text_sunday)

            2 -> return ApplicationClass.myContext().getString(R.string.text_monday)

            3 -> return ApplicationClass.myContext().getString(R.string.text_tuesday)

            4 -> return ApplicationClass.myContext().getString(R.string.text_wednesday)

            5 -> return ApplicationClass.myContext().getString(R.string.text_thursday)

            6 -> return ApplicationClass.myContext().getString(R.string.text_friday)
        }

        return ApplicationClass.myContext().getString(R.string.text_saturday)
    }

    private fun todayDay(): Int {
        return todayTime.dayOfMonth
    }

    fun selectedSearchDateFormatted(year: Int, month: Int, day: Int): String {
        val selectedSearchDate = LocalDate.of(year, month, day)
        return formatter.format(selectedSearchDate)
    }

    fun yesterdayFormatted(): String {
        return yesterdayTime.format(formatter)
    }

    fun todayFormatted(): String {
        return todayTime.format(formatter)
    }

    fun todayYear(): Int {
        return todayTime.year
    }

    fun todayMonth(): Int {
        return todayTime.monthValue
    }

    fun todayHour(): Int {
        return todayTime.hour
    }

    fun todayMinute(): Int {
        return todayTime.minute
    }

    fun todayOtherFormatted(): String {
        return "${todayYear()}년 ${todayMonth()}월 ${todayDay()}일 ${getDayOfWeek()}요일"
    }
}