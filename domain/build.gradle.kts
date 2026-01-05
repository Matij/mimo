plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android.gradle)
}

apply(from = "$rootDir/gradle/common-android-library.gradle")

android {
    namespace = "fm.mimo.domain"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}