package com.natlight.coctails.models

import com.natlight.coctails.utils.Constants.DATA_VIEW_TYPE
import java.io.Serializable

open class Drink(
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String
) : Serializable {

    open fun getType(): Int {
        return DATA_VIEW_TYPE
    }
}