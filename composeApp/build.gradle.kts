import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("app.cash.sqldelight") version "2.0.1"
    id("com.google.gms.google-services") version "4.4.1"
    id("com.google.firebase.crashlytics") version "2.9.9"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            
            // SQLDelight
            implementation("app.cash.sqldelight:android-driver:2.0.1")
            implementation("app.cash.sqldelight:sqlcipher:2.0.1")
            
            // Firebase
            implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
            implementation("com.google.firebase:firebase-auth-ktx")
            implementation("com.google.firebase:firebase-firestore-ktx")
            implementation("com.google.firebase:firebase-analytics-ktx")
            implementation("com.google.firebase:firebase-crashlytics-ktx")
            
            // DataStore
            implementation("androidx.datastore:datastore-preferences:1.0.0")
            
            // Coil
            implementation("io.coil-kt:coil-compose:2.5.0")
        }
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            
            // SQLDelight
            implementation("app.cash.sqldelight:runtime:2.0.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
            
            // Koin
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("io.insert-koin:koin-compose:1.1.0")
            
            // Ktor
            implementation("io.ktor:ktor-client-core:2.3.8")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.8")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            
            // DateTime
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            
            // Coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            
            // Navigation
            implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
            implementation("cafe.adriel.voyager:voyager-koin:1.0.0")
            
            // Charts
            implementation("com.patrykandpatrick.vico:compose:1.13.1")
            implementation("com.patrykandpatrick.vico:compose-m3:1.13.1")
        }
        
        iosMain.dependencies {
            // SQLDelight
            implementation("app.cash.sqldelight:native-driver:2.0.1")
            
            // Ktor
            implementation("io.ktor:ktor-client-darwin:2.3.8")
        }
    }
}

android {
    namespace = "com.shashank.expense.tracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.shashank.expense.tracker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

sqldelight {
    databases {
        create("ExpenseTrackerDatabase") {
            packageName.set("com.shashank.expense.tracker.db")
        }
    }
}

