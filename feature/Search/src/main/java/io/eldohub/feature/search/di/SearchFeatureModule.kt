package io.eldohub.feature.search.di

import io.eldohub.feature.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SearchFeatureModule = module {
    viewModel { SearchViewModel(get()) } // inject SearchUseCases
}
