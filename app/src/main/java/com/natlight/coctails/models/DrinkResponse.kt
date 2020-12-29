package com.natlight.coctails.models

import java.io.Serializable

data class DrinkResponse(
    val drinks: MutableList<Drink>
) : Serializable