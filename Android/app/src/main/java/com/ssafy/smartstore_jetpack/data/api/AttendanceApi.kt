package com.ssafy.smartstore_jetpack.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AttendanceApi {

    @POST("rest/attendance/mark")
    suspend fun postAttendanceMark(
        @Query("userId") userId: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Response<Boolean>

    @GET("rest/attendance/{userId}/{year}/{month}")
    suspend fun getAttendances(
        @Path("userId") userId: String,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): Response<List<Boolean>>
}