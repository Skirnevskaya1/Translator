package ru.gb.mytranslator.presentation

import ru.gb.domain.models.DataModel

sealed class AppState {
    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
