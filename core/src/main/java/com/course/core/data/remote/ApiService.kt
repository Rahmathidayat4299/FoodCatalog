package com.course.core.data.remote


import com.course.core.data.Receipes
import com.course.core.utils.Constants.Companion.BASE_URL
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 *hrahm,23/04/2024, 18:44
 **/
interface ApiService {

    @GET("recipes")
    suspend fun getReceipes(): Response<Receipes>

    @GET("recipes/{id}")
    suspend fun getReceipeDetails(@Path("id") id: Int?): Response<Receipes.Recipe>

    companion object {
        @JvmStatic
        operator fun invoke(

        ): ApiService {
            val hostname = "dummyjson.com"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/XgN0BIZQtZWR41iuNsVuOCfhfPY8TvLkdqWNobXsOuA=")
                .build()
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}