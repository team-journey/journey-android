package org.journey.android.network

import io.reactivex.rxjava3.core.Single
import org.journey.android.findpw.data.RequestChangePasswordDTO
import org.journey.android.findpw.data.ResponseChangePasswordDTO
import org.journey.android.findpw.data.ResponseVerificationCodeDTO
import org.journey.android.login.data.RequestEmailSignInDTO
import org.journey.android.login.data.ResponseEmailSignInDTO
import org.journey.android.login.data.ResponseGoogleSignInDTO
import org.journey.android.login.data.ResponseKakaoSignInDTO
import org.journey.android.main.dto.ResponseHomeDTO
import org.journey.android.signup.data.ResponseSignupDTO
import org.journey.android.signup.data.RequestSignupDTO
import org.journey.android.signup.data.RequestSocialSignUpDTO
import org.journey.android.signup.data.ResponseSocialSignUpDTO
import retrofit2.http.*

interface RetrofitInterface {
    @POST("/api/signin")
    fun signInEmail(@Header("token") fcmToken : String, @Body requestEmailSignInDTO: RequestEmailSignInDTO) : Single<ResponseEmailSignInDTO>

    @POST("/api/kakao")
    fun kakaoLogin(@Header("idToken") accessToken : String ,
                   @Header("token") fcmToken : String) : Single<ResponseKakaoSignInDTO>

    @POST("/api/google")
    fun googleLogin(@Header("idToken") accessToken : String ,
                    @Header("token") fcmToken : String) : Single<ResponseGoogleSignInDTO>

    @POST("/api/signup")
    fun signUp(@Header("fcmToken") fcmToken : String, @Body requestSignupDTO: RequestSignupDTO) : Single<ResponseSignupDTO>

    @POST("api/{snsType}/signup")
    fun snsSignUp(@Header("idToken") accessToken : String ,
                    @Header("token") fcmToken : String,
                  @Path("snsType") snsType : String,
    @Body requestSocialSignUpDTO: RequestSocialSignUpDTO) : Single<ResponseSocialSignUpDTO>

    @PUT("/api/password")
    fun changePassWord(@Body requestChangePasswordDTO: RequestChangePasswordDTO) : Single<ResponseChangePasswordDTO>

    @GET("/api/password/{email}")
    fun sendVerificationCode(@Path("email") email : String) : Single<ResponseVerificationCodeDTO>

    @GET("/api/home")
    fun getHomeResource(@Header("clent") clent : String) : Single<ResponseHomeDTO>

}