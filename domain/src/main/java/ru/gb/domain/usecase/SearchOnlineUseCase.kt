package ru.gb.domain.usecase

import ru.gb.domain.model.DataModel

interface SearchOnlineUseCase {
    suspend fun search(word: String): List<DataModel>
}