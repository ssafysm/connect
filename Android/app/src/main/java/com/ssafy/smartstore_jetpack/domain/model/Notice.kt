package com.ssafy.smartstore_jetpack.domain.model

import java.util.UUID

sealed class Notice(val id: String = UUID.randomUUID().toString()) {

    data class NoticeInfo(
        val title: String,
        val content: String,
        val time: String
    ): Notice()
}