plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.gb.data"
}

dependencies {
    implementation (project(Modules.domain))

    //Kotlin
    implementation(Kotlin.core)

    //Retrofit 2
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.adapter_coroutines)
    implementation(Retrofit.logging_interceptor)

    //Room
    implementation(Room.runtime)
    kapt(Room.compiler)
    implementation(Room.room_ktx)
}
