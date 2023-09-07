package com.example.mid_prep_ps.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.mid_prep_ps.BmiData
import com.example.mid_prep_ps.BmiDatabase
import com.example.mid_prep_ps.BmiRepository
import com.example.mid_prep_ps.repository.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BmiViewModel(application: Application) : ViewModel() {
    val repository:BmiRepository
    val allBmi : LiveData<List<BmiData>>
//    private var _bmiDateValues =MutableLiveData<List<Pair<Float,Float>>>()
//    val bmiDateMap = _bmiDateValues

    init {
        val dao = BmiDatabase.getDatabase(application).getBmiDao()
        repository=BmiRepository(dao)
        allBmi=repository.allBmi
    }
//    fun addBmiDatePair(date:Float,bmi:Float ){
//        _bmiDateValues.value = _bmiDateValues.value?.plus(Pair(date,bmi))?: listOf(Pair(date,bmi))
//    }
//    fun getBmiDatePair(): List<Pair<Float, Float>>? {
//        return bmiDateMap.value
//    }
    fun deleteBmi(bmiData: BmiData) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(bmiData)
    }
    fun addBmi(bmiData: BmiData) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(bmiData)
    }

}
class BmiViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BmiViewModel(application) as T
    }
}