package com.example.mid_prep_ps.viewmodels

import androidx.lifecycle.*
import com.example.mid_prep_ps.util.Resource
import com.example.mid_prep_ps.models.FilterResponse
import com.example.mid_prep_ps.models.Meal
import com.example.mid_prep_ps.models.RecipeResponse
//import com.example.mid_prep_ps.models.Recipe
import com.example.mid_prep_ps.repository.RecipesRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class RecipesViewModel(private val repository: RecipesRepository):ViewModel(){
    val filters = MutableLiveData<Resource<FilterResponse>>()
    val recipes = MutableLiveData<Resource<RecipeResponse>>()


    fun search(filterQuery: String) = viewModelScope.launch {
        safeSearch(filterQuery)
    }
    private suspend fun safeSearch(filterQuery: String) {
        filters.postValue(Resource.Loading())

        try {
//            if (hasInternetConnection()) {
                val response = repository.search(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
//            } else {
//                filters.postValue(Resource.Error(message = "No internet connection"))
//            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }
    private fun handleFilterResponse(response: Response<FilterResponse>): Resource<FilterResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }
    fun getIngredients(meal: Meal): String {

        var ingredients = ""

        if (meal.strIngredient1?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure1 + " " + meal.strIngredient1 + "\n"
        }
        if (meal.strIngredient2?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure2 + " " + meal.strIngredient2 + "\n"
        }
        if (meal.strIngredient3?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure3 + " " + meal.strIngredient3 + "\n"
        }
        if (meal.strIngredient4?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure4 + " " + meal.strIngredient4 + "\n"
        }
        if (meal.strIngredient5?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure5 + " " + meal.strIngredient5 + "\n"
        }
        if (meal.strIngredient6?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure6 + " " + meal.strIngredient6 + "\n"
        }
        if (meal.strIngredient7?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure7 + " " + meal.strIngredient7 + "\n"
        }
        if (meal.strIngredient8?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure8 + " " + meal.strIngredient8 + "\n"
        }
        if (meal.strIngredient9?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure9 + " " + meal.strIngredient9 + "\n"
        }
        if (meal.strIngredient10?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure10 + " " + meal.strIngredient10 + "\n"
        }
        if (meal.strIngredient11?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure11 + " " + meal.strIngredient11 + "\n"
        }
        if (meal.strIngredient12?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure12 + " " + meal.strIngredient12 + "\n"
        }
        if (meal.strIngredient13?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure13 + " " + meal.strIngredient13 + "\n"
        }
        if (meal.strIngredient14?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure14 + " " + meal.strIngredient14 + "\n"
        }
        if (meal.strIngredient15?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure15 + " " + meal.strIngredient15 + "\n"
        }
        if (meal.strIngredient16?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure16 + " " + meal.strIngredient16 + "\n"
        }
        if (meal.strIngredient17?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure17 + " " + meal.strIngredient17 + "\n"
        }
        if (meal.strIngredient18?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure18 + " " + meal.strIngredient18 + "\n"
        }
        if (meal.strIngredient19?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure19 + " " + meal.strIngredient19 + "\n"
        }
        if (meal.strIngredient20?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure20 + " " + meal.strIngredient20 + "\n"
        }

        return ingredients

    }
    fun getRecipeById(id: String) = viewModelScope.launch {
        safeGetRecipe(id)
    }

    private suspend fun safeGetRecipe(id: String) {
        recipes.postValue(Resource.Loading())

        try {
//            if (hasInternetConnection()) {
                val response = repository.getRecipeById(id)
                recipes.postValue(handleRecipeResponse(response = response))
//            } else {
//                recipes.postValue(Resource.Error(message = "No internet connection"))
//            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> recipes.postValue(Resource.Error(message = "Network failure"))
                else -> recipes.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }
    private fun handleRecipeResponse(response: Response<RecipeResponse>): Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }
    fun getVideoId(meal: Meal): String? {

        val videoUrl=meal.strYoutube

        val expression =
            "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"

        if (videoUrl == null || videoUrl.trim { it <= ' ' }.isEmpty()) {
            return null
        }
        val pattern: Pattern = Pattern.compile(expression)
        val matcher: Matcher = pattern.matcher(videoUrl)
        try {
            if (matcher.find()) return matcher.group()
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
        return null
    }
}


class RecipesViewModelFactory(private val repository: RecipesRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipesViewModel(repository) as T
    }

}