package io.eldohub.feature.search.screen.main;

@kotlin.Metadata(mv = {2, 2, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a,\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a$\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00062\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0010"}, d2 = {"SearchScreen", "", "viewModel", "Lio/eldohub/feature/search/viewmodel/SearchViewModel;", "onArticleClick", "Lkotlin/Function1;", "Lio/eldohub/domain/newsFeed/model/Article;", "SearchHistoryChip", "text", "", "onSearchClick", "Lkotlin/Function0;", "onRemoveClick", "SearchArticleCard", "article", "onClick", "search_debug"})
public final class SearchScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SearchScreen(@org.jetbrains.annotations.NotNull()
    io.eldohub.feature.search.viewmodel.SearchViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super io.eldohub.domain.newsFeed.model.Article, kotlin.Unit> onArticleClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SearchHistoryChip(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSearchClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SearchArticleCard(@org.jetbrains.annotations.NotNull()
    io.eldohub.domain.newsFeed.model.Article article, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super io.eldohub.domain.newsFeed.model.Article, kotlin.Unit> onClick) {
    }
}