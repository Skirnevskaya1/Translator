package ru.gb.domain.datasource

import ru.gb.domain.model.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(dataModel: DataModel)
}