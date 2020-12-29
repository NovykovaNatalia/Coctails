package com.natlight.coctails.datasourcesfactories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.natlight.coctails.datasources.DrinkDataSource
import com.natlight.coctails.models.Drink

class DrinkDataSourceFactory(private val context: Context) : DataSource.Factory<Int, Drink>() {

    val mutableLiveData = MutableLiveData<DrinkDataSource>()

    override fun create(): DataSource<Int, Drink> {
        val drinkDataSource = DrinkDataSource(context)
        mutableLiveData.postValue(drinkDataSource)
        return drinkDataSource
    }

}