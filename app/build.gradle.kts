import com.android.tools.build.bundletool.commands.DebugKeystoreUtils.DEBUG_KEY_PASSWORD

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("kapt")
}

private val localProperties =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)

fun getLocalProperty(key: String, defaultValue: String = ""): String =
    localProperties.getProperty(key, System.getenv(key) ?: defaultValue)
// Run jacoco gradle
// Combination between DSL and gradle
apply(from = "${rootProject.projectDir}/jacoco.gradle")
android {
    namespace = "com.example.jpmorgantest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jpmorgantest"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystore/debug/keystore.debug")
            keyAlias = "debugKey"
            keyPassword = getLocalProperty("DEBUG_KEY_PASSWORD")
            storePassword = getLocalProperty("DEBUG_KEY_PASSWORD")
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Dagger
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    // View model DI
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Android KTX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0-alpha02")

    // UI
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-alpha08")
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Constraint
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:4.2.0")

    // View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coil
    implementation("io.coil-kt:coil-compose:1.4.0")

    // -- Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.9.1")

    // Unit test
    testImplementation("junit:junit:4.13.2")

    // Shared Preference
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Permission
    implementation("com.google.accompanist:accompanist-permissions:0.25.0")


    // AndroidX Test Core library for testing Android components
    androidTestImplementation("androidx.test:core:1.5.0")

    // AndroidX Test JUnit Runner for running AndroidJUnit4 tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("app.cash.turbine:turbine:1.0.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
project.afterEvaluate {
    this.tasks.getByName("clean").dependsOn("installGitHooks")
    this.tasks.getByName("assemble").dependsOn("installGitHooks")
    tasks.create("executeTest") {
        group = "verification"
        dependsOn("test")
    }
    tasks.create("executeValidations") {
        group = "verification"
        dependsOn("lintKotlin")
        dependsOn("detekt")
        dependsOn("executeTest") /* Execute all test */
    }
    tasks.create("formatAndValidate") {
        group = "verification"
        dependsOn("formatKotlin")
        dependsOn("executeValidations")
        tasks.getByName("executeValidations").mustRunAfter("formatKotlin")
    }
    tasks.create("copyGitHooks", Copy::class.java) {
        description = "Copies the git hooks from team-props/git-hooks to the .git folder."
        from("${rootDir}/team-props/git-hooks") {
            include("**/*.sh")
            rename("(.*).sh", "$1")
        }
        into("${rootDir}/.git/hooks")
    }
    tasks.create("installGitHooks", Exec::class.java) {
        description = "Installs the pre-commit git hooks from team-props/git-hooks."
        group = "git hooks"
        workingDir = rootDir
        commandLine = listOf("chmod")
        args("-R", "+x", ".git/hooks/")
        dependsOn("copyGitHooks")
        doLast {
            logger.info("---- Git hook installed successfully.")
        }
    }
}