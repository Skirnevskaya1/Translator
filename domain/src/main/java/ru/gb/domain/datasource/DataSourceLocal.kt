package ru.gb.domain.datasource

import ru.gb.domain.models.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(dataModel: DataModel)
}