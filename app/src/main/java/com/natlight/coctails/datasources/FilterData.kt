package com.natlight.coctails.datasources

object FilterData {
    val filterMap: MutableMap<String, Boolean> = mutableMapOf()
    lateinit var currentFilter: String
}