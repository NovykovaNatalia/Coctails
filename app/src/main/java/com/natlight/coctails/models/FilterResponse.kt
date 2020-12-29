package com.natlight.coctails.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FilterResponse(
    @SerializedName("drinks")
    val filters: List<Filter>
) : Serializable