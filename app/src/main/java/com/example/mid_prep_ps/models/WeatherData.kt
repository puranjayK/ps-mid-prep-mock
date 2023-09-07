package com.example.mid_prep_ps.models

data class WeatherData(
    val daily: Daily?,
    val daily_units: DailyUnits?,
    val elevation: Float,
    val generationtime_ms: Float,
    val hourly: Hourly?,
    val hourly_units: HourlyUnits?,
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)