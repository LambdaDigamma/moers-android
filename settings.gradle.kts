dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Mein Moers"

include(":core")
include(":newsfeature")
include(":app")
