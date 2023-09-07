package com.example.mid_prep_ps.repository

import com.example.mid_prep_ps.api.WeatherRetrofitHelper
import com.example.mid_prep_ps.models.DefaultResponse
import com.example.mid_prep_ps.models.WeatherResponse
import retrofit2.Response

class WeatherRepository {

    suspend fun getWeather(lat:Float, long:Float, sDate:String, eDate:String, hourly:String, daily:String,timezone: String) : Response<WeatherResponse> {
        print("Puranjay get weather repo")
        return WeatherRetrofitHelper.api.getRainfall(lat,long,sDate,eDate, hourly, daily,timezone)
    }
    suspend fun check() :Response<WeatherResponse>{
        return WeatherRetrofitHelper.api.check()
    }
}