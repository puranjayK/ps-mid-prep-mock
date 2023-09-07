package com.example.mid_prep_ps

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmiTable")
class BmiData(
    @ColumnInfo(name = "timestamp") val time: Float,
    @ColumnInfo(name = "bmi") val bmi: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}