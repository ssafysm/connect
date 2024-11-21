package com.ssafy.smartstore_jetpack.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import com.ssafy.smartstore_jetpack.data.entity.OrderResponse
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonUtils {

    private val idRegex = "^[a-zA-Z0-9]{2,12}$".toRegex()
    private val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,16}$".toRegex()

    fun validateId(id: CharSequence): Boolean = idRegex.matches(id)

    fun validatePassword(password: CharSequence): Boolean =
        passwordRegex.matches(password)

    fun makeComma(num: Int): String {
        val comma = DecimalFormat("#,###")
        return "￦${comma.format(num)}"
    }

    fun makeCommaWon(num: Int): String {
        val comma = DecimalFormat("#,###")
        return "${comma.format(num)}원"
    }

    fun deleteComma(num: String): Int {
        return num.replace("원", "").replace("￦", "").replace(" ", "").replace(",", "").toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateFormat(time: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        val dateTime =
            OffsetDateTime.parse(time, inputFormatter).withOffsetSameInstant(ZoneOffset.ofHours(9))

        return dateTime.format(outputFormatter)
    }

    //날짜 포맷 출력
    fun dateformatYMDHM(time: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    fun dateformatYMD(time: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    // 시간 계산을 통해 완성된 제품인지 확인
    fun isOrderCompleted(order: OrderResponse): String {
        return if (checkTime(order.orderDate.time)) "주문완료" else "진행 중.."
    }

    private fun checkTime(time: Long): Boolean {
        val curTime = (Date().time + 60 * 60 * 9 * 1000)

        return (curTime - time) > ApplicationClass.ORDER_COMPLETED_TIME
    }

    // 주문 목록에서 총가격, 주문 개수 구하여 List로 반환한다.
    fun calcTotalPrice(orderList: List<OrderResponse>): List<OrderResponse> {
        orderList.forEach { order ->
            calcTotalPrice(order)
        }
        return orderList
    }

    fun calcTotalPrice(order: OrderResponse): OrderResponse {
        order.details.forEach { detail ->
            order.totalPrice += detail.sumPrice
            order.orderCount += detail.quantity
        }
        return order
    }
}