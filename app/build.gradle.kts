plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.gb.mytranslator"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.gb.mytranslator"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(Modules.utils))
    implementation(project(Modules.domain))
    implementation(project(Modules.core))
    implementation(project(Modules.data))

    implementation(Design.appcompat)
    implementation(Design.material)
    implementation(Design.swiperefreshlayout)
    implementation(Design.constraintlayout)


    implementation(Koin.koin_android)
    implementation(Koin.koin_java_compat)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //Room
    implementation(Room.runtime)
    kapt(Room.compiler)
    implementation(Room.room_ktx)

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    kapt ("com.github.bumptech.glide:compiler:4.11.0")

    //Coil
    implementation(Coil.coil)
}
