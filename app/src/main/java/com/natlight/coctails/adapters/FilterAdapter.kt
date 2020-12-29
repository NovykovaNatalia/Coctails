package com.natlight.coctails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natlight.coctails.R
import com.natlight.coctails.datasources.FilterData
import kotlinx.android.synthetic.main.filter_row.view.*

class FilterAdapter(private val context: Context, private val listFilters: List<String>) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.filter_row, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(listFilters.get(position))
    }

    override fun getItemCount(): Int {
        return listFilters.size
    }

    class FilterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(str: String?) {
            if (str != null) {
                itemView.title_filter.text = str
                itemView.checkBox.isChecked = FilterData.filterMap.getValue(str)
            }
        }
    }
}