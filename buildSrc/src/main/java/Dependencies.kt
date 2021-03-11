import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.example.gbandroidpro"
    const val compile_sdk = 30
    const val min_sdk = 24
    const val target_sdk = 30
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    // Features
    const val main = ":main"
    const val history = ":history"
    const val description = ":description"
    const val koin = ":koin"
}

object Versions {
    // Design
    const val appcompat = "1.2.0"
    const val material = "1.3.0"
    const val swipeRefreshLayout = "1.1.0"
    const val constraintLayout = "2.0.4"

    // Kotlin
    const val core = "1.3.2"
    const val stdlib = "1.4.21"
    const val coroutinesCore = "1.4.1"
    const val coroutinesAndroid = "1.4.1"

    //RxJava
    const val rxAndroid = "2.1.0"
    const val rxJava = "2.2.9"

    // Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.8.2"
    const val interceptor = "4.9.0"
    const val adapterRxJava = "1.0.0"
    const val adapterCoroutines = "0.9.2"

    // Koin
    const val koinAndroid = "2.0.1"
    const val koinViewModel = "2.0.1"

    // Glide
    const val glide = "4.11.0"
    const val glideCompiler = "4.9.0"

    // Room
    const val roomKtx = "2.3.0-beta02"
    const val runtime = "2.3.0-beta02"
    const val roomCompiler = "2.3.0-beta02"

    //Google Play
    const val playCore = "1.10.0"
}


object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object RxJava {
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val adapter_rx_java =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.adapterRxJava}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val koin_android = "org.koin:koin-android:${Versions.koinAndroid}"
    const val koin_view_model = "org.koin:koin-android-viewmodel:${Versions.koinViewModel}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object GooglePlay {
    const val core = "com.google.android.play:core:${Versions.playCore}"
}