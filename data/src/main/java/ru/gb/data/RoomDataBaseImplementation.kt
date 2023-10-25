package ru.gb.data

import ru.gb.data.dto.SearchResultDto
import ru.gb.data.room.HistoryDao
import ru.gb.domain.datasource.DataSourceLocal
import ru.gb.domain.models.DataModel

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResultDto>> {
    override suspend fun saveToDB(dataModel: DataModel) {
        convertDataModelSuccessToEntity(dataModel).let {
            historyDao.insert(it)
        }
    }

    override suspend fun getData(word: String): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }
}
