package ru.gb.core.viewModel

interface Interactor<T> {
    suspend fun getData(word: String, fromLocalSource: Boolean): T

}
