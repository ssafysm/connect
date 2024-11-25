package com.ssafy.smartstore_jetpack.presentation.views.main.attendance

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.core.content.withStyledAttributes
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.util.CalendarUtils.getDateColor
import com.ssafy.smartstore_jetpack.presentation.util.CalendarUtils.getDayOfWeekColor
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

@SuppressLint("ClickableViewAccessibility")
class CalendarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.calendarItemViewStyle,
    defStyleRes: Int = R.style.Calendar_CalendarItemViewStyle,
    private val year: Int = 0,
    private val month: Int = 0,
    private val day: Int = 0,
    private val attendanceMark: Boolean = false,
    private val dayOfWeek: String = "",
    private val viewModel: MainViewModel? = null
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val dayBounds = Rect()
    private val dayOfWeekBounds = Rect()
    private val dayPaint = TextPaint()
    private val dayOfWeekPaint = TextPaint()

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            setBackgroundColor(context.resources.getColor(R.color.neutral_white, context.theme))
            dayPaint.apply {
                isAntiAlias = true
                textSize = getDimensionPixelSize(
                    R.styleable.CalendarView_calendarItemTextSize,
                    0
                ).toFloat()
                color = getDateColor(year, month, day)
                typeface = getFont(R.styleable.CalendarView_calendarItemFontFamily)
                if (attendanceMark) {
                    setBackgroundResource(R.drawable.ic_attendance_mark)
                }
            }
            dayOfWeekPaint.apply {
                isAntiAlias = true
                textSize = getDimensionPixelSize(
                    R.styleable.CalendarView_calendarItemTextSize,
                    0
                ).toFloat()
                color = getDayOfWeekColor(dayOfWeek)
                typeface = getFont(R.styleable.CalendarView_calendarItemFontFamily)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (day == -1) return

        if (dayOfWeek == "") {
            val nowDate = day.toString()
            dayPaint.getTextBounds(nowDate, 0, nowDate.length, dayBounds)
            canvas.drawText(
                nowDate,
                (width / 2 - dayBounds.width() / 2).toFloat() - 2,
                (height / 2 + dayBounds.height() / 2).toFloat(),
                dayPaint
            )
        } else {
            dayOfWeekPaint.getTextBounds(dayOfWeek, 0, dayOfWeek.length, dayOfWeekBounds)
            canvas.drawText(
                dayOfWeek,
                (width / 2 - dayOfWeekBounds.width() / 2).toFloat() - 2,
                (height / 2 + dayOfWeekBounds.height() / 2).toFloat(),
                dayOfWeekPaint
            )
            when (viewModel?.appThemeName?.value) {
                "봄" -> setBackgroundColor(
                    context.resources.getColor(
                        R.color.spring_secondary,
                        context.theme
                    )
                )

                "여름" -> setBackgroundColor(
                    context.resources.getColor(
                        R.color.summer_secondary,
                        context.theme
                    )
                )

                "가을" -> setBackgroundColor(
                    context.resources.getColor(
                        R.color.autumn_secondary,
                        context.theme
                    )
                )

                "겨울" -> setBackgroundColor(
                    context.resources.getColor(
                        R.color.winter_secondary,
                        context.theme
                    )
                )

                else -> setBackgroundColor(
                    context.resources.getColor(
                        R.color.main_end,
                        context.theme
                    )
                )
            }
        }
    }
}