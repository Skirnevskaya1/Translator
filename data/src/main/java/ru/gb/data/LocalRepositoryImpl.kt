package ru.gb.data

import ru.gb.data.room.HistoryDao
import ru.gb.domain.LocalRepository
import ru.gb.domain.models.DataModel

class LocalRepositoryImpl(private val historyDao: HistoryDao) : LocalRepository {
    override suspend fun saveToDB(dataModel: DataModel) {
        historyDao.insert(dataModel.toHistoryEntity())
    }

    override suspend fun getAll(): List<DataModel> {
        return historyDao.all().toListDataModelConvert()
    }

    override suspend fun getDataByWord(word: String): DataModel {
        return historyDao.getDataByWord(word).toDataModel()
    }
}