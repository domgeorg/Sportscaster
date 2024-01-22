plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryHiltGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_domain"
}

dependencies {
    implementation(project(":core-data"))
    resources()
    retrofit()
}