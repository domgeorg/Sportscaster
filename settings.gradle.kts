pluginManagement {
    repositories {
        google()
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

rootProject.name = "Sportscaster"
include(":app")
include(":core-navigation")
include(":core-network")
include(":core-database")
include(":core-data")
include(":core-domain")
include(":core-resources")
include(":core-designsystem")
include(":feature-home")
