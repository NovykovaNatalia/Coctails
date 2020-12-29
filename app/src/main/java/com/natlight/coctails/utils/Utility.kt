package com.natlight.coctails.utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.natlight.coctails.datasources.FilterData
import com.natlight.coctails.models.Drink
import com.natlight.coctails.models.Filter

object Utility {

    private var progressBar: ProgressBar? = null

    fun Context.isInternetAvailable(): Boolean {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                true
            } else {
                showErrorToast("Internet not available. Please try again")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun Context.showErrorToast(message: String?) {

        try {
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

            val textView = toast.view.findViewById(android.R.id.message) as? TextView
            textView?.setTextColor(Color.WHITE)
            textView?.gravity = Gravity.CENTER

            toast.view.setBackgroundColor(Color.RED)

            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)

            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Context.showProgressBar() {
        try {
            val layout =
                (this as? Activity)?.findViewById<View>(android.R.id.content)?.rootView as? ViewGroup

            progressBar = ProgressBar(this, null, R.attr.progressBarStyleLarge)
            progressBar?.let { pb ->
                pb.isIndeterminate = true

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val rl = RelativeLayout(this)

                rl.gravity = Gravity.CENTER
                rl.addView(pb)

                layout?.addView(rl, params)

                pb.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressBar() {
        try {
            progressBar?.let {
                it.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fillFilterMap(filterList: List<String>) {
        for (filter in filterList) {
            if (!FilterData.filterMap.keys.contains(filter))
                FilterData.filterMap.put(filter, false)
        }
    }

    fun nextFilter(): String {
        val filteredFilters = FilterData.filterMap.filterValues { b -> b }
        val currentPosition = filteredFilters.keys.indexOf(FilterData.currentFilter)
        if (currentPosition < 0 && filteredFilters.isNotEmpty() ||
            filteredFilters.size == 1 && filteredFilters.keys.first()
                .equals(FilterData.currentFilter)
        ) {
            FilterData.currentFilter = filteredFilters.keys.first()
            return FilterData.currentFilter
        }
        if (currentPosition < filteredFilters.size - 1) {
            if (filteredFilters.size == 1 && !FilterData.currentFilter.equals(
                    filteredFilters.keys.toList().get(currentPosition)
                )
            ) {
                FilterData.currentFilter = filteredFilters.keys.toList().get(currentPosition)
                return FilterData.currentFilter
            }
            FilterData.currentFilter = filteredFilters.keys.toList().get(currentPosition + 1)
            return FilterData.currentFilter
        } else {
            return ""
        }
    }

    fun refreshCurrentFilter() {
        val filteredFilters = FilterData.filterMap.filterValues { b -> b }
        if (filteredFilters.isNotEmpty()) {
            FilterData.currentFilter = filteredFilters.keys.first()
        }
    }

    fun addHeader(title: String, drinks: MutableList<Drink>): MutableList<Drink> {
        var drinksList: MutableList<Drink> = mutableListOf()
        drinksList.add(object : Drink(title, "", "") {
            override fun getType(): Int {
                return Constants.HEADER_VIEW_TYPE
            }
        })
        drinksList.addAll(drinks)
        return drinksList
    }

    fun converDrinktoStringList(filters: List<Filter>): MutableList<String> {
        val filterList = mutableListOf<String>()
        for (filter in filters) {
            filterList.add(filter.strCategory)
        }
        return filterList
    }
}