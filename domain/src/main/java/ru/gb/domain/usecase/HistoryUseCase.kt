package ru.gb.domain.usecase

import ru.gb.domain.models.DataModel

interface HistoryUseCase {
    suspend fun getAll(): List<DataModel>

    suspend fun getByWord(word: String): DataModel
}