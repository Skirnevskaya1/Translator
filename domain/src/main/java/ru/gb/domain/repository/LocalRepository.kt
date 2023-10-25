package ru.gb.domain.repository

import ru.gb.domain.models.DataModel

interface LocalRepository {
    suspend fun saveToDB(dataModel: DataModel)
    suspend fun getAll(): List<DataModel>
    suspend fun getDataByWord(word : String): DataModel
}