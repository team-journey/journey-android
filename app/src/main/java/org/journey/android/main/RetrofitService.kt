package org.journey.android.main

import okhttp3.OkHttpClient
import org.journey.android.challenge.api.ChallengeService
import org.journey.android.diary.service.DiaryDeleteService
import org.journey.android.diary.service.DiaryPrivateDetailService
import org.journey.android.diary.service.DiaryPrivateService
import org.journey.android.diary.service.DiaryWriteService
import org.journey.android.main.model.MainApi
import org.journey.android.reward.api.RewardService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://3.36.55.247:5000"

    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val diaryWriteService: DiaryWriteService = retrofit.create(DiaryWriteService::class.java)
    val mainService: MainApi = retrofit.create(MainApi::class.java)
    val diaryPrivateDetailService: DiaryPrivateDetailService =
        retrofit.create(DiaryPrivateDetailService::class.java)
    val diaryPrivateService: DiaryPrivateService = retrofit.create(DiaryPrivateService::class.java)
    val diaryDeleteService: DiaryDeleteService = retrofit.create(DiaryDeleteService::class.java)

    val challengeService: ChallengeService = retrofit.create(ChallengeService::class.java)
    val rewardService: RewardService = retrofit.create(RewardService::class.java)


}