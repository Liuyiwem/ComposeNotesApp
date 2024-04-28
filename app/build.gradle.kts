plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.yiwenliu.composenotesapp"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "com.yiwenliu.composenotesapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleRuntime)

    implementation(platform(Compose.composeBom))
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.material3)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.activityCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.navigation)
    implementation(Compose.materialIconExtended)
    implementation(Compose.hiltNavigationCompose)

    implementation(Kotlin.coroutineCore)
    implementation(Kotlin.coroutineAndroid)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
    annotationProcessor(Room.roomCompiler)
    kapt(Room.roomCompiler)

    implementation(Google.material)

    testImplementation(Testing.androidTestCore)
    testImplementation(Testing.junit4)
    testImplementation(Testing.archCoreTesting)
    testImplementation(Testing.coroutinesTest)
    testImplementation(Testing.truth)
    testImplementation(Testing.mockk)
    testImplementation(Testing.turbine)

    androidTestImplementation(Testing.androidTestCore)
    androidTestImplementation(Testing.testRunner)
    androidTestImplementation(Testing.archCoreTesting)
    androidTestImplementation(Testing.composeUiTestJunit4)
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Testing.coroutinesTest)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockkAgent)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Testing.uiTestManifest)
}