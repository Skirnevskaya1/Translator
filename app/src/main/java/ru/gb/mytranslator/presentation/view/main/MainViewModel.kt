package ru.gb.mytranslator.presentation.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.gb.domain.usecase.HistoryUseCase
import ru.gb.domain.usecase.SearchOnlineUseCase
import ru.gb.mytranslator.presentation.AppState
import ru.gb.mytranslator.presentation.BaseViewModel

class MainViewModel(
    private val searchOnlineUseCase: SearchOnlineUseCase,
    private val historyUseCase: HistoryUseCase
) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getData(word: String, fromLocalSource: Boolean) {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            val appState = if (fromLocalSource) {
                val list = historyUseCase.getByWord(word)
                if (list == null) {
                    AppState.Success(emptyList())
                } else {
                    AppState.Success(listOf(list))
                }
            } else {
                AppState.Success(searchOnlineUseCase.search(word))
            }

            _mutableLiveData.postValue(
                appState
            )
        }
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}
