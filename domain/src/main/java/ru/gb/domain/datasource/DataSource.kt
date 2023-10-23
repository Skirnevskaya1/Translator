package ru.gb.domain.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}