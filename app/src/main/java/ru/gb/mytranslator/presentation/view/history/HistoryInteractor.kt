package ru.gb.mytranslator.presentation.view.history

import ru.gb.core.viewModel.Interactor
import ru.gb.data.dto.SearchResultDto
import ru.gb.data.mapSearchResultToResult
import ru.gb.domain.AppState
import ru.gb.domain.repository.Repository
import ru.gb.domain.repository.RepositoryLocal


class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
): Interactor<AppState> {
    override suspend fun getData(word: String, fromLocalSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromLocalSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}