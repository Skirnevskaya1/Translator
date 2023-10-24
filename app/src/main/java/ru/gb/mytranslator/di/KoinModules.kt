package ru.gb.mytranslator.di

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gb.data.LocalRepositoryImpl
import ru.gb.data.RemoteRepositoryImpl
import ru.gb.data.RepositoryImplementation
import ru.gb.data.RepositoryImplementationLocal
import ru.gb.data.RetrofitImplementation
import ru.gb.data.RoomDataBaseImplementation
import ru.gb.data.dto.SearchResultDto
import ru.gb.data.room.HistoryDataBase
import ru.gb.domain.LocalRepository
import ru.gb.domain.RemoteRepository
import ru.gb.domain.repository.Repository
import ru.gb.domain.repository.RepositoryLocal
import ru.gb.domain.usecase.HistoryUseCase
import ru.gb.domain.usecase.HistoryUseCaseImpl
import ru.gb.domain.usecase.SearchOnlineUseCase
import ru.gb.domain.usecase.SearchOnlineUseCaseImpl
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

    single<LocalRepository> {
        LocalRepositoryImpl(get<HistoryDataBase>().historyDao())
    }

    single<RemoteRepository> {
        RemoteRepositoryImpl(RetrofitImplementation())
    }

//    single <SearchOnlineUseCase>{
//        SearchOnlineUseCaseImpl(get(),get())
//    }

    single<HistoryUseCase> {
        HistoryUseCaseImpl(get())
    }
}

val mainScreen = module {
    scope(named<MainFragment>()) {
        scoped { MainInteractor(get(), get()) }
        scoped { SearchOnlineUseCaseImpl(get(), get()) as SearchOnlineUseCase }
        scoped { HistoryUseCaseImpl(get()) as HistoryUseCase }

        viewModel { MainViewModel(get(), get(), get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryFragment>()) {
        scoped { HistoryInteractor(get(), get()) }
        scoped { SearchOnlineUseCaseImpl(get(), get()) as SearchOnlineUseCase }
        viewModel { HistoryViewModel(get(), get()) }
    }
}
