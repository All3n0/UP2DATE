pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "UP2DATE"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:ui")
include(":domain")
include(":data")
include(":feature:newsFeed")
project(":feature:newsFeed").projectDir = file("feature/NewsFeed")

include(":feature:favourites")
project(":feature:favourites").projectDir = file("feature/Favourites")

include(":feature:articles")
project(":feature:articles").projectDir = file("feature/Articles")

 