plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android.gradle)
}

apply(from = "$rootDir/gradle/common-android-library.gradle")

android {
    namespace = "fm.mimo.data"
}

dependencies {
    implementation(project(":libraries:di-android"))
    api(project(":libraries:rest"))
    api(project(":domain"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
