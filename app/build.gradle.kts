import org.jetbrains.kotlin.util.collectionUtils.forEachScope

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin
    alias(libs.plugins.hilt.plugin)

    // KAPT plugin
    alias(libs.plugins.kaptKotlin)
    // KSP plugin
    alias(libs.plugins.kspkotlin)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)

}

android {
    namespace = "com.doaa.mosalam.librarymanagementsystem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.doaa.mosalam.librarymanagementsystem"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "CLIENT_SERVER_ID",
            "\"569681346766-j8cs6jngcs9kis5tuj2rln0mf384n4k2.apps.googleusercontent.com\""
        )

//        <string name="facebook_app_id">1133601105320249</string>
//        <string name="fb_login_protocol_scheme">fb1133601105320249</string>
//        <string name="facebook_client_token">3b9098897b7b86792bf108bc55337b78</string>

        buildConfigField ("String", "FACEBOOK_APP_ID", "\"1133601105320249\"")
        buildConfigField("String","FB_LOGIN_PROTOCOL_SCHEME","\"fb1133601105320249\"")
        buildConfigField ("String", "FACEBOOK_CLIENT_TOKEN", "\"3b9098897b7b86792bf108bc55337b78\"")



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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Enable view binding and data binding
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.activity)
    // Splash Screen
    implementation(libs.androidx.core.splashscreen)
    // Firebase
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.auth)
    implementation(libs.facebook.android.sdk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // recycler View
    implementation(libs.androidx.recyclerview)
    //cart view
    implementation(libs.androidx.cardview)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

//view model
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.navigation.fragment)


    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //room database
    implementation(libs.androidx.room.runtime)

//    ksp(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // circle image view

    implementation(libs.circleimageview)

    //countyCode
    implementation(libs.ccp)
}