package com.natlight.coctails.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natlight.coctails.R
import com.natlight.coctails.adapters.FilterAdapter
import com.natlight.coctails.api.ApiClient
import com.natlight.coctails.datasources.FilterData
import com.natlight.coctails.models.FilterResponse
import com.natlight.coctails.utils.Utility
import kotlinx.android.synthetic.main.filter_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity() {

    private lateinit var listFilters: RecyclerView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        context = this
        supportActionBar?.title = "Filters"
        supportActionBar?.setDisplayShowHomeEnabled(true)

        listFilters = findViewById(R.id.filter_recycler_main)
        listFilters.setHasFixedSize(true)
        listFilters.layoutManager = LinearLayoutManager(context)

        requestFilters()

        val btnApply: Button = findViewById(R.id.btnApply)
        btnApply.setOnClickListener(View.OnClickListener {
            listFilters.forEach { view ->
                FilterData.filterMap.set(view.title_filter.text.toString(), view.checkBox.isChecked)
            }
            finish()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun requestFilters() {
        if (FilterData.filterMap.size <= 1) {
            ApiClient.apiService.getFilters().enqueue(object : Callback<FilterResponse> {
                override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                    Utility.hideProgressBar()
                }

                override fun onResponse(
                    call: Call<FilterResponse>,
                    response: Response<FilterResponse>
                ) {
                    Utility.hideProgressBar()
                    val filterResponse = response.body()
                    val filtersList = filterResponse?.filters?.let {
                        Utility.converDrinktoStringList(it)
                    }

                    filtersList?.let { Utility.fillFilterMap(it) }
                    val adapter = filtersList?.let { FilterAdapter(context, it) }
                    adapter?.notifyDataSetChanged()
                    listFilters.adapter = adapter
                }
            })
        } else {
            val adapter =
                FilterData.filterMap.keys.toMutableList()?.let { FilterAdapter(context, it) }
            adapter?.notifyDataSetChanged()
            listFilters.adapter = adapter
        }
    }
}
