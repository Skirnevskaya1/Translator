package ru.gb.mytranslator.di

import androidx.room.Room
import gb.ru.repository.RepositoryImplementation
import gb.ru.repository.RepositoryImplementationLocal
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gb.data.RetrofitImplementation
import ru.gb.data.RoomDataBaseImplementation
import ru.gb.data.dto.SearchResultDto
import ru.gb.data.room.HistoryDataBase
import ru.gb.domain.repository.Repository
import ru.gb.domain.repository.RepositoryLocal
import ru.gb.mytranslator.presentation.view.history.HistoryFragment
import ru.gb.mytranslator.presentation.view.history.HistoryInteractor
import ru.gb.mytranslator.presentation.view.history.HistoryViewModel
import ru.gb.mytranslator.presentation.view.main.MainFragment
import ru.gb.mytranslator.presentation.view.main.MainInteractor
import ru.gb.mytranslator.presentation.view.main.MainViewModel


val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<SearchResultDto>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<RepositoryLocal<List<SearchResultDto>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }
}

val mainScreen = module {
    scope(named<MainFragment>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryFragment>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}
