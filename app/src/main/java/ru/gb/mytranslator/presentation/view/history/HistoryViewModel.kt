package ru.gb.mytranslator.presentation.view.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.gb.domain.usecase.HistoryUseCase
import ru.gb.mytranslator.presentation.AppState
import ru.gb.mytranslator.presentation.BaseViewModel

class HistoryViewModel(
    private val historyUseCase: HistoryUseCase
) :
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
            delay(1000L)
            startInteractor()
        }

    }

    private suspend fun startInteractor() {
        _mutableLiveData.postValue(
                AppState.Success(historyUseCase.getAll())
        )
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }
}