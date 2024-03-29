plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}
apply(from = "buildTypes.gradle")

android {
    compileSdkVersion(33)

    defaultConfig {
        applicationId = "com.marturelo.themoviedbapp"
        minSdkVersion(23)
        targetSdkVersion(33)
        versionCode = 1
        versionName = "1.1"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding {
        this.isEnabled = true
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    testImplementation("junit:junit:4.12")

    /* Google-Android libraries */
    implementation("androidx.appcompat:appcompat:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.vectordrawable:vectordrawable:1.0.0")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")

    implementation("com.jakewharton.timber:timber:4.7.1")

    // Dagger
    implementation("com.google.dagger:dagger:2.25.4")
    kapt("com.google.dagger:dagger-compiler:2.25.4")
    implementation("com.google.dagger:dagger-android-support:2.25.4")
    kapt("com.google.dagger:dagger-android-processor:2.25.4")

    //Test
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("io.mockk:mockk:1.10.2")

    //rx
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")
    implementation("io.reactivex.rxjava2:rxjava:2.1.12")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle:3.1.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-android-lifecycle:3.1.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-components:3.1.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-android:3.1.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-kotlin:3.1.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-android-lifecycle-kotlin:3.1.0")

    //okHttp
    implementation("com.squareup.okhttp3:okhttp:4.2.1")
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.1")

    //epoxy
    implementation("com.airbnb.android:epoxy:4.1.0")
    kapt("com.airbnb.android:epoxy-processor:4.1.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

    //room
    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    implementation("androidx.room:room-rxjava2:2.2.5")
    implementation("android.arch.persistence.room:runtime:1.1.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")

}