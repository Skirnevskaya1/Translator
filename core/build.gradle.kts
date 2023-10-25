plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.gb.core"
}

dependencies {

    implementation(project(Modules.utils))
    implementation(project(Modules.domain))

    implementation(Design.appcompat)

    implementation(Koin.koin_android)
    implementation(Koin.koin_java_compat)
}