package com.ssafy.smartstore_jetpack.presentation.util

import androidx.annotation.ColorInt
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import java.util.Calendar

object CalendarUtils {

    const val DAYS_PER_WEEK = 7
    const val WEEKS_PER_MONTH = 6 + 1

    private val dateFormatConverter = DateFormatConverter()

    fun getDaysOfMonth(year: Int, month: Int, attendances: List<Boolean>): List<Pair<Int, Boolean>> {
        val result = MutableList(42) { Pair(-1, false) }
        val newCalendar = Calendar.getInstance()

        var nowDate = 1
        newCalendar.set(year, month - 1, nowDate)
        while (true) {
            val nowWeek = newCalendar.get(Calendar.WEEK_OF_MONTH)
            val nowDay = newCalendar.get(Calendar.DAY_OF_WEEK)
            result[((nowWeek - 1) * 7) + nowDay - 1] = Pair(newCalendar.get(Calendar.DATE), attendances[nowDate - 1])

            nowDate++
            if (nowDate > newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) break

            newCalendar.set(year, month - 1, nowDate)
        }

        return result
    }

    @ColorInt
    fun getDateColor(year: Int, month: Int, day: Int): Int {
        val newCalendar = Calendar.getInstance()
        newCalendar.set(year, month - 1, day)
        return when (newCalendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.sub_cancel,
                    ApplicationClass.myContext().theme
                )
            }

            7 -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.sub_submit,
                    ApplicationClass.myContext().theme
                )
            }

            else -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.neutral_100,
                    ApplicationClass.myContext().theme
                )
            }
        }
    }

    @ColorInt
    fun getDayOfWeekColor(dayOfWeek: String): Int {
        return when (dayOfWeek) {
            ApplicationClass.myContext().getString(R.string.text_sunday) -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.sub_cancel,
                    ApplicationClass.myContext().theme
                )
            }

            ApplicationClass.myContext().getString(R.string.text_saturday) -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.sub_submit,
                    ApplicationClass.myContext().theme
                )
            }

            else -> {
                ApplicationClass.myContext().resources.getColor(
                    R.color.neutral_100,
                    ApplicationClass.myContext().theme
                )
            }
        }
    }
}