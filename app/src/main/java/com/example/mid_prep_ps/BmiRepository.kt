package com.example.mid_prep_ps

import androidx.lifecycle.LiveData

class BmiRepository(private val bmiDAO: BmiDAO) {

    val allBmi : LiveData<List<BmiData>> = bmiDAO.getAllBmi()

    suspend fun insert(bmiData: BmiData){
        bmiDAO.insert(bmiData)
    }

    suspend fun delete(bmiData: BmiData){
        bmiDAO.delete(bmiData)
    }


}