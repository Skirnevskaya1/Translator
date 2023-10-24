package ru.gb.data

import ru.gb.data.dto.SearchResultDto
import ru.gb.domain.datasource.DataSource
import ru.gb.domain.repository.Repository

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResultDto>>) :
    Repository<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}