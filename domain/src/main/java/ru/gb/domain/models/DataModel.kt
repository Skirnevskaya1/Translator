package ru.gb.domain.models

data class DataModel(
    val text: String = "",
    val meanings: List<Meaning> = listOf()
)
