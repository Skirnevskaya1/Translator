package ru.gb.domain.usecase

import ru.gb.domain.LocalRepository
import ru.gb.domain.models.DataModel

class HistoryUseCaseImpl(private val localRepository: LocalRepository) : HistoryUseCase {
    override suspend fun getAll(): List<DataModel> {
        return localRepository.getAll()
    }

    override suspend fun getByWord(word: String): DataModel {
        return localRepository.getDataByWord(word)
    }
}