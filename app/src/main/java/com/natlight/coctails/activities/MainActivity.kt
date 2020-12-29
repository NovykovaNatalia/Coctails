package com.natlight.coctails.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.natlight.coctails.R
import com.natlight.coctails.adapters.DrinksAdapter
import com.natlight.coctails.datasources.FilterData
import com.natlight.coctails.models.Drink
import com.natlight.coctails.utils.Constants.DEFAULT_FILTER
import com.natlight.coctails.utils.Utility
import com.natlight.coctails.viewmodels.DrinkViewModel
import com.natlight.coctails.viewmodelsfactories.DrinkViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drinkViewModel: DrinkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FilterData.filterMap.put(DEFAULT_FILTER, true)
        FilterData.currentFilter = DEFAULT_FILTER

        recycler_main.layoutManager = LinearLayoutManager(this@MainActivity)
        val adapter = DrinksAdapter(this)
        recycler_main.adapter = adapter

        drinkViewModel =
            ViewModelProvider(this, DrinkViewModelFactory(this)).get(DrinkViewModel::class.java)
        drinkViewModel.getData().observe(this, object : Observer<PagedList<Drink>> {
            override fun onChanged(t: PagedList<Drink>?) {
                adapter.submitList(t)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_drink_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.menu_drink) {
            val filterActivity = Intent(this, FilterActivity::class.java)
            startActivity(filterActivity)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        drinkViewModel.getMutableLiveData().value?.invalidate()
        Utility.refreshCurrentFilter()
    }

}
