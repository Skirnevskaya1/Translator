package ru.gb.domain.repository

import ru.gb.domain.models.DataModel

interface RemoteRepository {
    suspend fun search(word: String) : List<DataModel>
}