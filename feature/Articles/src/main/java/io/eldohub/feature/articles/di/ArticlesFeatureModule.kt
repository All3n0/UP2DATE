import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val ArticleFeatureModule =module {
    viewModelOf(::ArticleViewModel)
}