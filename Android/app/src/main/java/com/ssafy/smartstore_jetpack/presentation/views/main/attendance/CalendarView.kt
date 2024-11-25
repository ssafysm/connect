package com.ssafy.smartstore_jetpack.presentation.views.main.attendance

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.util.CalendarUtils.DAYS_PER_WEEK
import com.ssafy.smartstore_jetpack.presentation.util.CalendarUtils.WEEKS_PER_MONTH
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import kotlin.math.max

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.calendarViewStyle,
    defStyleRes: Int = R.style.Calendar_CalendarViewStyle,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val dayOfWeeks = listOf<String>("일", "월", "화", "수", "목", "금", "토")
    private var _calendarItemHeight: Float = 0F

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _calendarItemHeight = getDimension(R.styleable.CalendarView_calendarItemHeight, 0F)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = paddingTop + paddingBottom + max(
            suggestedMinimumHeight,
            (_calendarItemHeight * WEEKS_PER_MONTH).toInt()
        )
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childWidth = (width / DAYS_PER_WEEK).toFloat()
        val childHeight = (height / WEEKS_PER_MONTH).toFloat()
        var calendarItemIndex = 0

        children.forEach { view ->
            val itemLeft = (calendarItemIndex % DAYS_PER_WEEK) * childWidth
            val itemTop = (calendarItemIndex / DAYS_PER_WEEK) * childHeight

            view.layout(
                itemLeft.toInt(),
                itemTop.toInt(),
                (itemLeft + childWidth).toInt(),
                (itemTop + childHeight).toInt()
            )

            calendarItemIndex++
        }
    }

    fun initCalendarView(year: Int, month: Int, daysOfMonth: List<Pair<Int, Boolean>>, viewModel: MainViewModel) {
        for (dayOfWeek in dayOfWeeks) {
            addView(
                CalendarItemView(
                    context = context,
                    dayOfWeek = dayOfWeek,
                    viewModel = viewModel
                )
            )
        }
        for (index in daysOfMonth.indices) {
            addView(
                CalendarItemView(
                    context = context,
                    year = year,
                    month = month,
                    day = daysOfMonth[index].first,
                    attendanceMark = daysOfMonth[index].second,
                    viewModel = viewModel
                )
            )
        }
    }
}