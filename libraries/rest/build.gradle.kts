plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android.gradle)
}

apply(from = "$rootDir/gradle/common-android-library.gradle")

android {
    namespace = "fm.mimo.rest"
}

dependencies {
    implementation(project(":libraries:di-android"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    api(libs.retrofit)
    api(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
}
