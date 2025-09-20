package io.eldohub.up2date

import ArticleFeatureModule
import NewsFeedFeatureModule
import android.app.Application
import io.eldohub.feature.favourites.di.FavouritesFeatureModule
import io.eldohub.up2date.di.DataModule
import io.eldohub.up2date.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                DataModule,
                DomainModule,
                NewsFeedFeatureModule,
                ArticleFeatureModule,
                FavouritesFeatureModule
            )
        }
    }
}