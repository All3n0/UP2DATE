// di/FavouritesModule.kt
package io.eldohub.feature.favourites.di

import io.eldohub.feature.favourites.screen.viewmodels.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val FavouritesFeatureModule = module {
    viewModel { FavoriteViewModel(get()) } // `get()` injects Application
}
