package ru.gb.mytranslator.presentation.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gb.core.viewModel.BaseViewModel
import ru.gb.data.parseOnlineSearchResults
import ru.gb.domain.AppState

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, fromLocalSource: Boolean) {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, fromLocalSource) }
    }

    // Не обязательно использовать withContext для вызова Retrofit
    // если использована .addCallAdapterFactory(CoroutineCallAdapterFactory()).
    // То же самое применимо для Room
    private suspend fun startInteractor(word: String, fromLocalSource: Boolean) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(
                parseOnlineSearchResults(
                    interactor.getData(
                        word,
                        fromLocalSource
                    )
                )
            )
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}