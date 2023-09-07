package com.example.mid_prep_ps.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mid_prep_ps.models.DefaultResponse
import com.example.mid_prep_ps.models.WeatherResponse
import com.example.mid_prep_ps.repository.WeatherRepository
import com.example.mid_prep_ps.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class WeatherViewModel(private val repository: WeatherRepository):ViewModel() {
    val weather = MutableLiveData<Resource<WeatherResponse>>()

    fun search(lat:Float, long:Float, sDate:String, eDate:String, hourly:String, daily:String,timezone:String) = viewModelScope.launch {
        safeSearch(lat, long, sDate, eDate, hourly, daily,timezone)
    }
    private suspend fun safeSearch(lat:Float, long:Float, sDate:String, eDate:String, hourly:String, daily:String,timezone: String){
        weather.postValue(Resource.Loading())
        Log.i("Puranjay reponse","safe search")
        try {
//            if (hasInternetConnection()) {
            val response = repository.getWeather(lat, long, sDate, eDate, hourly, daily,timezone)
            val url = response.toString().substringAfter("url=","not found").substringBefore("}","not found")
            Log.i("Khanijo response", url)
            weather.postValue(handleWeatherResponse(response = response))
//            } else {
//                filters.postValue(Resource.Error(message = "No internet connection"))
//            }
        } catch (t: Throwable) {
            t.message?.let { Log.i("Puranjay vm", it) }

            when (t) {
                is IOException -> weather.postValue(Resource.Error(message = "Network failure"))
                else -> weather.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }
    private fun handleWeatherResponse(response: Response<WeatherResponse>): Resource<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }
}


class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}