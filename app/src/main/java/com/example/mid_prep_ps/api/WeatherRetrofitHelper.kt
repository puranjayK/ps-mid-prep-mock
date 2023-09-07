package com.example.mid_prep_ps.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://archive-api.open-meteo.com/"
class WeatherRetrofitHelper {
    companion object{
        private val retrofitInst by lazy {
//            val logging = HttpLoggingInterceptor()
//            logging.level = (HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build()
        }
        val api:WeatherAPI by lazy {
            retrofitInst.create(WeatherAPI::class.java)
        }
    }
}