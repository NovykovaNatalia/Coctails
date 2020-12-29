package com.natlight.coctails.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.natlight.coctails.datasources.DrinkDataSource
import com.natlight.coctails.datasourcesfactories.DrinkDataSourceFactory
import com.natlight.coctails.models.Drink

class DrinkViewModel(private val context: Context) : ViewModel() {

    private var drinks: LiveData<PagedList<Drink>> = MutableLiveData<PagedList<Drink>>()
    private var mutableLiveData = MutableLiveData<DrinkDataSource>()

    init {
        val factory: DrinkDataSourceFactory by lazy {
            DrinkDataSourceFactory(context)
        }
        mutableLiveData = factory.mutableLiveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(6)
            .build()

        drinks = LivePagedListBuilder(factory, config)
            .build()

    }

    fun getData(): LiveData<PagedList<Drink>> {
        return drinks
    }

    fun getMutableLiveData(): MutableLiveData<DrinkDataSource> {
        return mutableLiveData
    }
}