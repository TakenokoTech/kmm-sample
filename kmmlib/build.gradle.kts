import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

val kotlinVersion = rootProject.extra["kotlin_version"]!!
val coroutinesVersion = rootProject.extra["coroutines_version"]!!
val serializationVersion = rootProject.extra["serialization_version"]!!
val ktorVersion = rootProject.extra["ktor_version"]!!
val koinVersions = rootProject.extra["koin_versions"]!!

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

android {
    namespace = "tech.takenoko.kmmlib"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

kotlin {
    android()

    /*
    androidNativeX86 { binaries.sharedLib() }
    androidNativeX64 { binaries.sharedLib() }
    androidNativeArm32 { binaries.sharedLib() }
    androidNativeArm64 { binaries.sharedLib() }
    */

    XCFramework().apply {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "kmm"
                this@apply.add(this)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                 implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                 implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                 api("io.ktor:ktor-client-core:$ktorVersion")
                 api("io.ktor:ktor-client-json:$ktorVersion")
                 api("io.ktor:ktor-client-serialization:$ktorVersion")
                 api("io.insert-koin:koin-core:$koinVersions")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting
        val androidCommonMain by creating {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }

        val androidTest by getting
        val androidCommonTest by creating {
            dependsOn(commonTest)
            androidTest.dependsOn(this)
        }

        /*
        val androidNativeX86Main by getting
        val androidNativeX64Main by getting
        val androidNativeArm32Main by getting
        val androidNativeArm64Main by getting
        val androidNativeMain by creating {
             dependsOn(commonMain)
            androidNativeX86Main.dependsOn(this)
            androidNativeX64Main.dependsOn(this)
            androidNativeArm32Main.dependsOn(this)
            androidNativeArm64Main.dependsOn(this)
        }
        */

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosCommonMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }

        val iosSimulatorArm64Main by getting
        val iosSimulatorMain by creating {
            dependsOn(commonMain)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosCommonTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

val arm32FromFolder = File(buildDir, "bin/androidNativeArm32/releaseShared")
val arm64FromFolder = File(buildDir, "bin/androidNativeArm64/releaseShared")
val x86FromFolder = File(buildDir, "bin/androidNativeX86/releaseShared")
val x64FromFolder = File(buildDir, "bin/androidNativeX64/releaseShared")
val arrFromFolder = File(buildDir, "outputs/aar")

val arm32ToFolder = File(projectDir, "../app/src/main/cpp/libs/armeabi-v7a")
val arm6To4Folder = File(projectDir, "../app/src/main/cpp/libs/arm64-v8a")
val x86ToFolder = File(projectDir, "../app/src/main/cpp/libs/x86")
val x64ToFolder = File(projectDir, "../app/src/main/cpp/libs/x86_64")
val arrToFolder = File(projectDir, "../app/libs")

val targets = listOf(
    arm32FromFolder to arm32ToFolder,
    arm64FromFolder to arm6To4Folder,
    x86FromFolder to x86ToFolder,
    x64FromFolder to x64ToFolder,
    arrFromFolder to arrToFolder
)

val filExt = listOf("*.so", "*.h", "*.aar")

tasks.build {
    doFirst {
        println("===> doFirst")
        targets.map { (_, into) -> fileTree(into.path) { include(filExt) } }
            .flatten()
            .forEach { it.delete() }
    }
    doLast {
        println("===> doLast")
        targets.forEach { (from, into) ->
            copy {
                from(from)
                into(into)
                include(filExt)
            }
        }
    }
}

tasks.clean {
    doLast {
        targets.map { (_, into) -> fileTree(into.path) { include(filExt) } }
            .flatten()
            .forEach { it.delete() }
    }
}
