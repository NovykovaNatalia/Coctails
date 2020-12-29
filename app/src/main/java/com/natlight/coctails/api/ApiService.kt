package com.natlight.coctails.api

import com.natlight.coctails.models.DrinkResponse
import com.natlight.coctails.models.FilterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("filter.php")
    fun getDrinks(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("c") filter: String
    ): Call<DrinkResponse>

    @GET("list.php?c=list")
    fun getFilters(): Call<FilterResponse>

}