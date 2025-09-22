package io.eldohub.up2date.di

import AppDatabase
import io.eldohub.data.search.repository.SearchRepositoryImpl
import io.eldohub.data.article.datasource.ArticleLocalDataSource
import io.eldohub.data.article.datasource.ArticleLocalDataSourceImpl
import io.eldohub.data.article.repository.ArticleRepositoryImpl
import io.eldohub.data.common.InternetServiceImpl
import io.eldohub.data.common.network.Up2dateHttpClient
import io.eldohub.data.common.network.Up2dateHttpClientImpl
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSource
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSourceImpl
import io.eldohub.data.newsFeed.repository.NewsRepositoryImpl
import io.eldohub.data.search.database.SearchDatabase
import io.eldohub.data.search.datasource.SearchRemoteDataSource
import io.eldohub.data.search.datasource.SearchRemoteDataSourceImpl
import io.eldohub.domain.article.repository.ArticleRepository
import io.eldohub.domain.article.usecase.AddArticleUseCase
import io.eldohub.domain.article.usecase.DeleteArticleUseCase
import io.eldohub.domain.article.usecase.GetArticleByIdUseCase
import io.eldohub.domain.article.usecase.GetArticlesUseCase
import io.eldohub.domain.article.usecase.UpdateArticleUseCase
//import io.eldohub.data.newsFeed.dao.NewsDao
import io.eldohub.domain.common.InternetService
import io.eldohub.domain.newsFeed.repository.FetchNewsRepository
import io.eldohub.domain.newsFeed.usecase.FetchNewsUseCase
import io.eldohub.data.search.repository.SearchRepository
import io.eldohub.domain.search.usecase.ClearSearchHistoryUseCase
import io.eldohub.domain.search.usecase.GetSearchHistoryUseCase
import io.eldohub.domain.search.usecase.RemoveSearchQueryUseCase
import io.eldohub.domain.search.usecase.SaveSearchQueryUseCase
import io.eldohub.domain.search.usecase.SearchArticlesUseCase
import io.eldohub.domain.search.usecase.SearchUseCases
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import io.eldohub.feature.newsfeed.screen.viewmodels.NewsDetailsViewModel
import io.eldohub.feature.newsfeed.screen.viewmodels.NewsFeedViewModel
import io.eldohub.feature.search.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Data bindings
// Data bindings
val DataModule = module {
    // Http client
    single<Up2dateHttpClient> { Up2dateHttpClientImpl() }

    // Internet checker
    single<InternetService> { InternetServiceImpl(androidContext()) }

    // News
    single<RemoteNewsDataSource> { RemoteNewsDataSourceImpl(get()) }
    single<FetchNewsRepository> { NewsRepositoryImpl(get()) }

    // Articles
    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().articleDao() }
    single<ArticleLocalDataSource> { ArticleLocalDataSourceImpl(get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }

    // Search repository
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
    single { SearchDatabase.getInstance(androidContext()) }
    single { get<SearchDatabase>().searchHistoryDao() }
    single<io.eldohub.domain.search.repository.SearchRepository> {
        SearchRepositoryImpl(get(), get())
    } // provide DAO for SearchRepository

}



// Domain bindings
val DomainModule = module {
    // UseCase (needs repository + internet service)
    factory { FetchNewsUseCase(get(), get()) }

    // Article use cases
    factory { AddArticleUseCase(get()) }
    factory { UpdateArticleUseCase(get()) }
    factory { DeleteArticleUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
    factory { GetArticleByIdUseCase(get()) }

    // Search use cases
    factory { SearchArticlesUseCase(get()) }
    factory { SaveSearchQueryUseCase(get()) }
    factory { GetSearchHistoryUseCase(get()) }
    factory { ClearSearchHistoryUseCase(get()) }
    factory { RemoveSearchQueryUseCase(get()) }  // ✅ add this

    // Aggregated SearchUseCases container
    factory {
        SearchUseCases(
            searchArticles = get(),
            saveSearchQuery = get(),
            getSearchHistory = get(),
            clearSearchHistory = get(),
            removeFromSearchHistoryUseCase = get ()
        )
    }

    // ViewModels
    viewModel { ArticleViewModel(get()) }
    viewModel { NewsFeedViewModel(get()) }
    viewModel { NewsDetailsViewModel() }
    viewModel { SearchViewModel(get()) } // inject SearchUseCases
}
