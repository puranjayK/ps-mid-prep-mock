package com.example.mid_prep_ps.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//private const val BASE_URL = "https://tasty.p.rapidapi.com/recipes/"
private const val BASE_URL="https://www.themealdb.com"
class RecipeRetrofitHelper {
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
    val api:RecipeAPI by lazy {
        retrofitInst.create(RecipeAPI::class.java)
        }
    }

}