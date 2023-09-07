package com.example.mid_prep_ps

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BmiDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bmiData: BmiData)

    @Delete
    suspend fun delete(bmiData:BmiData)

    @Query("Select * from bmiTable order by timestamp")
    fun getAllBmi():LiveData<List<BmiData>>
}