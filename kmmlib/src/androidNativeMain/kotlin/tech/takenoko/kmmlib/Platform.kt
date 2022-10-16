package tech.takenoko.kmmlib

class AndroidNativePlatform : Platform {
    override val name: String = "Android"
}

actual fun getPlatform(): Platform = AndroidNativePlatform()
actual fun getPlatformName(): String = "Android"