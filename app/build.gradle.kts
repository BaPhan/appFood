plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.baphan.appdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.baphan.appdemo"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.room:room-runtime:2.3.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.google.android.material:material:1.2.0")

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth")

    implementation ("com.squareup.picasso:picasso:2.71828")


}