package com.ssafy.smartstore_jetpack.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceApi {

    @POST("rest/attendance/mark")
    suspend fun postAttendanceMark(
        @Path("userId") userId: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Response<Boolean>

    @GET("rest/attendance/{userId}/{year}/{month}")
    suspend fun getAttendances(
        @Path("userId") userId: String,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): Response<List<Boolean>>
}