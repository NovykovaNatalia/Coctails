package com.natlight.coctails.datasources

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.natlight.coctails.api.ApiClient
import com.natlight.coctails.models.Drink
import com.natlight.coctails.models.DrinkResponse
import com.natlight.coctails.utils.Utility
import com.natlight.coctails.utils.Utility.isInternetAvailable
import com.natlight.coctails.utils.Utility.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinkDataSource(private val context: Context) : PageKeyedDataSource<Int, Drink>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Drink>
    ) {
        if (context.isInternetAvailable()) {
            getDrinks(params, callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Drink>) {
        if (context.isInternetAvailable()) {
            val nextPageNo = params.key + 1
            getMoreDrinks(params, nextPageNo, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Drink>) {
        if (context.isInternetAvailable()) {
            val previousPageNo = if (params.key > 1) params.key - 1 else 0
            getMoreDrinks(params, previousPageNo, callback)
        }
    }

    private fun getDrinks(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Drink>
    ) {
        context.showProgressBar()
        val filter = FilterData.filterMap.filterValues { b -> b }.entries.first().key

        if (filter.isNotEmpty()) {
            ApiClient.apiService.getDrinks(1, params.requestedLoadSize, filter)
                .enqueue(object : Callback<DrinkResponse> {
                    override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                        Utility.hideProgressBar()
                    }

                    override fun onResponse(
                        call: Call<DrinkResponse>,
                        response: Response<DrinkResponse>
                    ) {
                        Utility.hideProgressBar()
                        val drinkResponse = response.body()
                        val drinks = drinkResponse?.drinks
                        var drinksList = drinks?.let { Utility.addHeader(filter, it) }

                        drinksList?.let { callback.onResult(it, null, 2) }
                    }
                })
        }
    }

    private fun getMoreDrinks(
        params: LoadParams<Int>,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, Drink>
    ) {
        val currentFilter = FilterData.currentFilter
        val nextFilter = Utility.nextFilter()

        if (nextFilter.isNotEmpty() && !nextFilter.equals(currentFilter)) {
            ApiClient.apiService.getDrinks(params.key, params.requestedLoadSize, nextFilter)
                .enqueue(object : Callback<DrinkResponse> {
                    override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<DrinkResponse>,
                        response: Response<DrinkResponse>
                    ) {
                        val drinkResponse = response.body()
                        val drinks = drinkResponse?.drinks
                        var drinksList = drinks?.let { Utility.addHeader(nextFilter, it) }

                        drinksList?.let { callback.onResult(it, previousOrNextPageNo) }
                    }

                })
        }
    }

}