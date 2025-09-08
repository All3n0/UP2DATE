import io.eldohub.feature.newsfeed.screen.viewmodels.NewsFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val NewsFeedFeatureModule =module {
    viewModelOf(::NewsFeedViewModel)
}