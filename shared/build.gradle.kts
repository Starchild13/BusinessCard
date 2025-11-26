import org.bouncycastle.pqc.crypto.lms.Composer.compose
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("com.android.lint")
}

kotlin {
    // -----------------------------
    // ANDROID TARGET
    // -----------------------------
    androidLibrary {
        namespace = "com.example.shared"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder { }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // -----------------------------
    // iOS targets + XCFramework
    // -----------------------------
    val xcf = XCFramework("SharedKit")

    val iosX64Target = iosX64()
    val iosArm64Target = iosArm64()
    val iosSimArm64Target = iosSimulatorArm64()

    iosX64Target.binaries.framework {
        baseName = "SharedKit"
        xcf.add(this)
    }
    iosArm64Target.binaries.framework {
        baseName = "SharedKit"
        xcf.add(this)
    }
    iosSimArm64Target.binaries.framework {
        baseName = "SharedKit"
        xcf.add(this)
    }

    // -----------------------------
    // Source sets
    // -----------------------------
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.21")

                // Compose Multiplatform
                implementation("org.jetbrains.compose.ui:ui:1.9.3")
                implementation("org.jetbrains.compose.ui:ui-graphics:1.9.3")
                implementation("org.jetbrains.compose.ui:ui-unit:1.9.3")

                implementation("org.jetbrains.compose.runtime:runtime:1.9.3")
                implementation("org.jetbrains.compose.foundation:foundation:1.9.3")

                // Shared Material3
                implementation("org.jetbrains.compose.material3:material3:1.9.0")
                implementation("org.jetbrains.compose.material3:material3-window-size-class:1.9.0")

                implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")




            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                implementation("androidx.core:core-ktx:1.17.0")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")

                // Match Compose version with MPP
                implementation("androidx.activity:activity-compose:1.12.0")
                implementation("androidx.compose.material3:material3:1.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
                implementation("androidx.test.espresso:espresso-web:3.7.0")
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation("androidx.test:runner:1.7.0")
                implementation("androidx.test:core:1.7.0")
                implementation("androidx.test.ext:junit:1.3.0")
                implementation("androidx.test.espresso:espresso-core:3.7.0")


                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
                implementation("io.ktor:ktor-client-mock:3.3.2")
            }
        }

        iosMain {
            dependencies {
                implementation("io.ktor:ktor-client-ios:3.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            }
        }
    }
}
