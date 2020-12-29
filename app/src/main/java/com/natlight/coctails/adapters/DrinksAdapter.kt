package com.natlight.coctails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.natlight.coctails.R
import com.natlight.coctails.databinding.DrinkRowBinding
import com.natlight.coctails.models.Drink
import com.natlight.coctails.utils.Constants.DATA_VIEW_TYPE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.drink_row.view.*
import kotlinx.android.synthetic.main.title_row.view.*

class DrinksAdapter(private val context: Context) :
    PagedListAdapter<Drink, RecyclerView.ViewHolder>(DRINK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (viewType == DATA_VIEW_TYPE) {
            val binding: DrinkRowBinding =
                DataBindingUtil.inflate(inflater, R.layout.drink_row, parent, false)
            return DrinkViewHolder(binding)
        } else {
            val binding: ViewBinding =
                DataBindingUtil.inflate(inflater, R.layout.title_row, parent, false)
            return TitleViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as DrinkViewHolder).bind(getItem(position))
        else (holder as TitleViewHolder).bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.getType()
    }

    class DrinkViewHolder(itemBinding: DrinkRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var binding: DrinkRowBinding? = null

        fun bind(drink: Drink?) {
            if (drink != null) {
                itemView.txt_drink_name.text = drink.strDrink
                if (!drink.strDrinkThumb.isNullOrEmpty())
                    Picasso.get().load(drink.strDrinkThumb).into(itemView.image_drink_thumb)
            }
        }

        init {
            this.binding = itemBinding
        }

    }

    class TitleViewHolder(itemBinding: ViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private var binding: ViewBinding? = null
        fun bind(drink: Drink?) {
            if (drink != null) {
                itemView.title_drinks.text = drink.strDrink
            }
        }

        init {
            this.binding = itemBinding
        }
    }

    companion object {
        private val DRINK_COMPARATOR = object : DiffUtil.ItemCallback<Drink>() {
            override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean =
                oldItem.idDrink.equals(newItem.idDrink)

            override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean =
                newItem.equals(oldItem)
        }
    }

}