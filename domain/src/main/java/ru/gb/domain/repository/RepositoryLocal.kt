package ru.gb.domain.repository

import ru.gb.domain.model.DataModel

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(dataModel: DataModel)
}