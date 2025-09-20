package io.eldohub.feature.favourites.screen.viewmodels;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, d2 = {"Lio/eldohub/feature/favourites/screen/viewmodels/FavoriteViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "<init>", "(Landroid/app/Application;)V", "repository", "Lio/eldohub/data/favorites/repository/FavoriteRepository;", "favorites", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lio/eldohub/data/favorites/entity/FavoriteEntity;", "getFavorites", "()Lkotlinx/coroutines/flow/StateFlow;", "addToFavorites", "", "article", "removeFromFavorites", "toggleFavorite", "favourites_debug"})
public final class FavoriteViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final io.eldohub.data.favorites.repository.FavoriteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<io.eldohub.data.favorites.entity.FavoriteEntity>> favorites = null;
    
    public FavoriteViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<io.eldohub.data.favorites.entity.FavoriteEntity>> getFavorites() {
        return null;
    }
    
    public final void addToFavorites(@org.jetbrains.annotations.NotNull()
    io.eldohub.data.favorites.entity.FavoriteEntity article) {
    }
    
    public final void removeFromFavorites(@org.jetbrains.annotations.NotNull()
    io.eldohub.data.favorites.entity.FavoriteEntity article) {
    }
    
    public final void toggleFavorite(@org.jetbrains.annotations.NotNull()
    io.eldohub.data.favorites.entity.FavoriteEntity article) {
    }
}