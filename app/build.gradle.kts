plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
apply(from = "buildTypes.gradle")

android {
    compileSdkVersion(33)

    defaultConfig {
        applicationId = "com.marturelo.themoviedbapp"
        minSdkVersion(21)
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

}