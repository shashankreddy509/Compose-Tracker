import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
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
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                
                // SQLDelight
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.primitive)
                
                // Koin Core
                implementation(libs.insert.koin.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                
                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)
                
                // DateTime
                implementation(libs.jetbrains.kotlinx.datetime)
                
                // Coroutines
                implementation(libs.kotlinx.coroutines.core)
                
                // Navigation
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.koin)
                implementation(libs.voyager.transitions)
                
                // Charts
                implementation(libs.compose)
                implementation(libs.compose.m3)

                // Firebase
                implementation(libs.firebase.auth)
                implementation(libs.firebase.firestore)
                implementation(libs.stately.common)

                
                // Stately dependencies
//                implementation("co.touchlab:stately-common:2.2.0")
//                implementation("co.touchlab:stately-isolate:2.2.0")
//                implementation("co.touchlab:stately-concurrency:2.2.0")
            }
        }
        
        val androidMain by getting {
            dependencies {
                // Android-specific Compose
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                
                // Android-specific Koin
                implementation(libs.koin.android)

                // SQLDelight
                implementation(libs.sqldelight.android)
                implementation(libs.android.database.sqlcipher)

                
                // DataStore
                implementation(libs.androidx.datastore.preferences)
                
                // Coil
                implementation(libs.coil.compose)
            }
        }
        
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // iOS-specific SQLDelight
                implementation(libs.sqldelight.native)
                
                // iOS-specific Ktor
                implementation(libs.ktor.client.darwin)
            }
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
            dialect("app.cash.sqldelight:sqlite-3-18-dialect:2.0.1")
            verifyMigrations.set(true)
        }
    }
}

