package ru.gb.data

import ru.gb.data.db.HistoryDao
import ru.gb.domain.models.DataModel
import ru.gb.domain.repository.LocalRepository

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