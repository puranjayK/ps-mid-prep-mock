package com.example.mid_prep_ps.api

import com.example.mid_prep_ps.models.DefaultResponse
import com.example.mid_prep_ps.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/v1/archive")
    suspend fun getRainfall(@Query("latitude") lat:Float,
                            @Query("longitude") long:Float,
                            @Query("start_date") sDate:String,
                            @Query("end_date") eDate:String,
                            @Query("hourly") hourly:String,
                            @Query("daily") daily:String,
                            @Query("timezone") timezone :String
                            ):Response<WeatherResponse>
    @GET("archive?latitude=52.52&longitude=13.41&start_date=2023-08-19&end_date=2023-09-02&hourly=temperature_2m")
    suspend fun check():Response<WeatherResponse>
}