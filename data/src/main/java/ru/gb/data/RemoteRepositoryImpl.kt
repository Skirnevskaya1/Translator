package ru.gb.data

import ru.gb.data.dto.SearchResultDto
import ru.gb.domain.RemoteRepository
import ru.gb.domain.datasource.DataSource
import ru.gb.domain.model.DataModel

class RemoteRepositoryImpl(private val dataSource: DataSource<List<SearchResultDto>>) :
    RemoteRepository {
    override suspend fun search(word: String): List<DataModel> {
        return dataSource.getData(word).toListDataModel()
    }
}