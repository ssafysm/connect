package com.ssafy.smartstore_jetpack.presentation.views.main.attendance

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentAttendanceBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.CalendarUtils.getDaysOfMonth
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class AttendanceFragment : BaseFragment<FragmentAttendanceBinding>(R.layout.fragment_attendance) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initViews()
    }

    private fun initViews() {
        binding.tvDateAttendance.text = "${viewModel.year.value}년 ${viewModel.month.value}월 출석 현황입니다."
        binding.calendarViewCustomCalendar.initCalendarView(
            viewModel.year.value,
            viewModel.month.value,
            getDaysOfMonth(viewModel.year.value, viewModel.month.value, viewModel.attendances.value),
            viewModel
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }
}