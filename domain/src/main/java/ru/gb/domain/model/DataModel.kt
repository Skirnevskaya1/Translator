package ru.gb.domain.model

data class DataModel(
    val text: String = "",
    val meanings: List<Meaning> = listOf()
)
