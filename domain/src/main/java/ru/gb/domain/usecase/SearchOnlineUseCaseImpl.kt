package ru.gb.domain.usecase

import ru.gb.domain.models.DataModel
import ru.gb.domain.repository.LocalRepository
import ru.gb.domain.repository.RemoteRepository

class SearchOnlineUseCaseImpl(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : SearchOnlineUseCase {
    override suspend fun search(word: String): List<DataModel> {
        val data = remoteRepository.search(word)
        if (data.isNotEmpty()) {
            localRepository.saveToDB(data.first())
        }
        return data
    }
}