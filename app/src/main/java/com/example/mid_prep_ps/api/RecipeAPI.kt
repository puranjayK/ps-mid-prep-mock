package com.example.mid_prep_ps.api

//import com.example.mid_prep_ps.models.Recipe
import com.example.mid_prep_ps.models.FilterResponse

import com.example.mid_prep_ps.models.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RecipeAPI {

    @GET("/api/json/v1/1/search.php")
    suspend fun getRecipes(@Query("s") search:String) : Response<FilterResponse>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecipeById(
        @Query("i")
        id:String
    ) : Response<RecipeResponse>
}