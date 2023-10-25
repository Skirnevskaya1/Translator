package ru.gb.domain.repository

import ru.gb.domain.models.DataModel

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(dataModel: DataModel)
}