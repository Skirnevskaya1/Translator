package ru.gb.domain

import ru.gb.domain.models.DataModel

interface RemoteRepository {
    suspend fun search(word: String) : List<DataModel>
}