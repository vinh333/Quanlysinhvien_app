pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter();
        mavenCentral()
        maven("https://jitpack.io")

    }
}

rootProject.name = "Quanlysinhvien_app"
include(":app")
