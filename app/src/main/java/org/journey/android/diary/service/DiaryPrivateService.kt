package org.journey.android.diary.service

import org.journey.android.diary.dto.ResponseDiaryPrivateData
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DiaryPrivateService {
    @GET("/api/feed/{year}/{month}")
    fun getPrivateDiary(
        @Path("year") year: String,
        @Path("month") month: String,
        @Header("Content-Type") contenttype: String,
        @Header("Bearer") jwt: String,
    ) : Call<ResponseDiaryPrivateData>

    @DELETE("/api/feed/{id}")
    fun deletePrivateDetail(
        @Path("id") id:Int,
        @Header("Content-Type") contenttype: String,
        @Header("Bearer") jwt: String,
    ) : Call<Unit>
}