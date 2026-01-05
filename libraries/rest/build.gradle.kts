plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/gradle/common-android-library.gradle")

android {
    namespace = "fm.mimo.rest"
}

dependencies {
    implementation(project(":libraries:di-android"))
    implementation(libs.hilt.android)

    api(libs.retrofit)
    api(libs.converter.gson)
    implementation(libs.logging.interceptor)
}
