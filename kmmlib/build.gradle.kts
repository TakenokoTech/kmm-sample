import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    namespace = "tech.takenoko.kmmlib"
    compileSdk = 32
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}

kotlin {
//    android()
    androidNativeX86 { binaries.sharedLib() }
    androidNativeX64 { binaries.sharedLib() }
    androidNativeArm32 { binaries.sharedLib() }
    androidNativeArm64 { binaries.sharedLib() }

    XCFramework().apply {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "kmmlib"
                this@apply.add(this)
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

//        val androidMain by getting
//        val androidTest by getting

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

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            dependsOn(iosMain)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

val arm32SoFolder = File(buildDir, "bin/androidNativeArm32/releaseShared")
val arm64SoFolder = File(buildDir, "bin/androidNativeArm64/releaseShared")
val x86SoFolder = File(buildDir, "bin/androidNativeX86/releaseShared")
val x64SoFolder = File(buildDir, "bin/androidNativeX64/releaseShared")

//val jniArm32Folder = File(projectDir, "../app/src/main/jniLibs/armeabi-v7a")
//val jniArm64Folder = File(projectDir, "../app/src/main/jniLibs/arm64-v8a")
//val jniX86Folder = File(projectDir, "../app/src/main/jniLibs/x86")
//val jniX64Folder = File(projectDir, "../app/src/main/jniLibs/x86_64")
val jniArm32Folder = File(projectDir, "../app/src/main/cpp/libs/armeabi-v7a")
val jniArm64Folder = File(projectDir, "../app/src/main/cpp/libs/arm64-v8a")
val jniX86Folder = File(projectDir, "../app/src/main/cpp/libs/x86")
val jniX64Folder = File(projectDir, "../app/src/main/cpp/libs/x86_64")

val targets = listOf(
    arm32SoFolder to jniArm32Folder,
    arm64SoFolder to jniArm64Folder,
    x86SoFolder to jniX86Folder,
    x64SoFolder to jniX64Folder,
)

val nativeFiles = listOf("*.so", "*.h")

tasks.build {
    doFirst {
        print("===> doFirst")
        targets.map { (_, into) -> fileTree(into.path) { include(nativeFiles) } }
            .flatten()
            .forEach { it.delete() }
    }
    doLast {
        print("===> doLast")
        targets.forEach { (from, into) ->
            copy {
                from(from)
                into(into)
                include(nativeFiles)
            }
        }
    }
}

tasks.clean {
    doLast {
        targets.map { (_, into) -> fileTree(into.path) { include(nativeFiles) } }
            .flatten()
            .forEach { it.delete() }
    }
}