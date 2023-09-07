package com.example.mid_prep_ps.repository

import com.example.mid_prep_ps.api.RecipeRetrofitHelper
import com.example.mid_prep_ps.models.FilterResponse
import com.example.mid_prep_ps.models.RecipeResponse
import retrofit2.Response

//import com.example.mid_prep_ps.models.Recipe

class RecipesRepository() {
    suspend fun search(searchQuery:String): Response<FilterResponse>{
        return RecipeRetrofitHelper.api.getRecipes(searchQuery)
    }

    suspend fun getRecipeById(id:String): Response<RecipeResponse>{
        return RecipeRetrofitHelper.api.getRecipeById(id)
    }
}