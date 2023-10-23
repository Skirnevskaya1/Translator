package ru.gb.mytranslator.presentation.view.main

import ru.gb.core.viewModel.Interactor
import ru.gb.data.dto.SearchResultDto
import ru.gb.data.mapSearchResultToResult
import ru.gb.domain.AppState
import ru.gb.domain.repository.Repository
import ru.gb.domain.repository.RepositoryLocal


class MainInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromLocalSource: Boolean): AppState {
        val appState: AppState

        if (fromLocalSource) {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        } else {
            val listDataModel = mapSearchResultToResult(repositoryRemote.getData(word))
            appState = AppState.Success(listDataModel)
            if (listDataModel.isNotEmpty()) {
                repositoryLocal.saveToDB(listDataModel.first())
            }
        }

        return appState
    }
}