package com.ssafy.smartstore_jetpack.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.smartstore_jetpack.data.entity.AlarmEntity
import com.ssafy.smartstore_jetpack.domain.model.Alarm
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.dateFormat

object AlarmsMapper {

    operator fun invoke(alarmEntities: List<AlarmEntity>): List<Alarm> {
        val newAlarms = mutableListOf<Alarm>()

        alarmEntities.forEach { alarmEntity ->
            newAlarms.add(
                Alarm(
                    id = alarmEntity.id.toString(),
                    userId = alarmEntity.userId ?: "",
                    title = alarmEntity.title ?: "",
                    content = alarmEntity.content ?: "",
                    sendTime = dateFormat(alarmEntity.sentTime)
                )
            )
        }

        return newAlarms
    }
}