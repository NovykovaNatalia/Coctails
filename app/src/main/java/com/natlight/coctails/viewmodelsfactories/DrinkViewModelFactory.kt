package com.natlight.coctails.viewmodelsfactories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.natlight.coctails.viewmodels.DrinkViewModel

class DrinkViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinkViewModel(context) as T
    }

}