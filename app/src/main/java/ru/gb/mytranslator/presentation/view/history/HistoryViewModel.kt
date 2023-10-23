package ru.gb.mytranslator.presentation.view.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import ru.gb.core.viewModel.BaseViewModel
import ru.gb.data.parseLocalSearchResults
import ru.gb.domain.AppState

class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, fromLocalSource: Boolean) {
        _mutableLiveData.postValue(AppState.Loading)
        cancelJob()
        viewModelCoroutineScope.launch {
            delay(3000L)
            startInteractor(word, fromLocalSource) }

    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(
            parseLocalSearchResults(
                interactor.getData(
                    word,
                    isOnline
                )
            )
        )
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }
}