package ru.gb.domain.usecase

import ru.gb.domain.models.DataModel

interface SearchOnlineUseCase {
    suspend fun search(word: String): List<DataModel>
}