package com.ssafy.smartstore_jetpack.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginUser(
    @Json(name = "id")
    val id: String,

    @Json(name = "pass")
    val pass: String
)