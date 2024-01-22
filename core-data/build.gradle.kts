plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryHiltGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_data"
}

dependencies {
    implementation(project(":core-network"))
    implementation(project(":core-database"))
}