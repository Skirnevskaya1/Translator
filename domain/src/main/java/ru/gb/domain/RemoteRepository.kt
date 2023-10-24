package ru.gb.domain

import ru.gb.domain.model.DataModel

interface RemoteRepository {
    suspend fun search(word: String) : List<DataModel>
}