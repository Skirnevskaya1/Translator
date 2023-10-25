package ru.gb.data

import ru.gb.data.dto.SearchResultDto
import ru.gb.domain.datasource.DataSourceLocal
import ru.gb.domain.models.DataModel
import ru.gb.domain.repository.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultDto>>) :
    RepositoryLocal<List<SearchResultDto>> {
    override suspend fun saveToDB(dataModel: DataModel) {
        dataSource.saveToDB(dataModel)
    }

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}