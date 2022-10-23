plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    namespace = "tech.takenoko.nkmmlib"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

kotlin {
    android()
    androidNativeX86 { binaries.sharedLib() }
    androidNativeX64 { binaries.sharedLib() }
    androidNativeArm32 { binaries.sharedLib() }
    androidNativeArm64 { binaries.sharedLib() }

    sourceSets {
        val commonMain by getting
        val commonTest by getting

        val androidMain by getting
        val androidTest by getting

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
    }
}

val arm32FromFolder = File(buildDir, "bin/androidNativeArm32/releaseShared")
val arm64FromFolder = File(buildDir, "bin/androidNativeArm64/releaseShared")
val x86FromFolder = File(buildDir, "bin/androidNativeX86/releaseShared")
val x64FromFolder = File(buildDir, "bin/androidNativeX64/releaseShared")

val arm32ToFolder = File(projectDir, "../app/src/main/cpp/libs/armeabi-v7a")
val arm6To4Folder = File(projectDir, "../app/src/main/cpp/libs/arm64-v8a")
val x86ToFolder = File(projectDir, "../app/src/main/cpp/libs/x86")
val x64ToFolder = File(projectDir, "../app/src/main/cpp/libs/x86_64")

val targets = listOf(
    arm32FromFolder to arm32ToFolder,
    arm64FromFolder to arm6To4Folder,
    x86FromFolder to x86ToFolder,
    x64FromFolder to x64ToFolder
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
