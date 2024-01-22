plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryHiltGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_database"
}

dependencies {
    room()
}