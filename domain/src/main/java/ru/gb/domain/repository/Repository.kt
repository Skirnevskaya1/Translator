package ru.gb.domain.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}
