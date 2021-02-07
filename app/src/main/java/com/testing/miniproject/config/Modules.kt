package com.testing.miniproject.config

import com.testing.miniproject.data.DataRepository
import com.testing.miniproject.data.localdb.DataDAO
import com.testing.miniproject.domain.DataDAOImpl
import com.testing.miniproject.domain.DataRepositoryImpl
import com.testing.miniproject.presentation.detail.DetailViewModel
import com.testing.miniproject.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val uiModule = module  {
    //view models
    viewModel { DetailViewModel(get()) }
    viewModel { ListViewModel(get()) }
}

val repositoryModule = module {
    //repositories
    single<DataRepository> { DataRepositoryImpl(get()) }
}

val databaseModule = module {
    //dao
    single<DataDAO> { DataDAOImpl(get()) }
}

val remoteModule = module {
    single { provideRealm() }
}