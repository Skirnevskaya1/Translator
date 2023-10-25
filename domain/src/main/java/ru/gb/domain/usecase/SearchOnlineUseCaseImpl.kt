package ru.gb.domain.usecase

import ru.gb.domain.LocalRepository
import ru.gb.domain.RemoteRepository
import ru.gb.domain.models.DataModel

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