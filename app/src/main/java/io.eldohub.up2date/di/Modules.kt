package io.eldohub.up2date.di

import io.eldohub.data.common.InternetServiceImpl
import io.eldohub.data.common.network.Up2dateHttpClient
import io.eldohub.data.common.network.Up2dateHttpClientImpl
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSource
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSourceImpl
import io.eldohub.data.newsFeed.repository.NewsRepositoryImpl
//import io.eldohub.data.newsFeed.dao.NewsDao
import io.eldohub.domain.common.InternetService
import io.eldohub.domain.newsFeed.repository.FetchNewsRepository
import io.eldohub.domain.newsFeed.usecase.FetchNewsUseCase
import io.eldohub.feature.newsfeed.screen.viewmodels.NewsFeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Data bindings
val DataModule = module {
    // Http client
    single<Up2dateHttpClient> { Up2dateHttpClientImpl() }

    // Internet checker
    single<InternetService> { InternetServiceImpl(androidContext()) }

    // DAO (make sure AppDatabase is already bound somewhere else)
//    single<NewsDao> { get<io.eldohub.data.common.AppDatabase>().newsDao() }

    // Datasource
    single<RemoteNewsDataSource> { RemoteNewsDataSourceImpl(get()) }

    // Repository (needs both remote + dao)
    single<FetchNewsRepository> { NewsRepositoryImpl(get()) }
}

// Domain bindings
val DomainModule = module {
    // UseCase (needs repository + internet service)
    factory { FetchNewsUseCase(get(), get()) }

    // ViewModel
    viewModel { NewsFeedViewModel(get()) }
}
