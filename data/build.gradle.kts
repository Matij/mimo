plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/gradle/common-android-library.gradle")

android {
    namespace = "fm.mimo.data"
}

dependencies {
    implementation(project(":libraries:rest"))
    implementation(project(":domain"))
}