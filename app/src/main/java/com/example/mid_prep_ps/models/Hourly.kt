package com.example.mid_prep_ps.models

data class Hourly(
    val precipitation: List<Double>,
    val rain: List<Double>,
    val time: List<String>
)