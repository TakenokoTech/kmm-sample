package tech.takenoko.nkmmlib

data class AndroidNativePlatform(
    override val name: String
): Platform {
    constructor(): this("AndroidNative")
}

actual fun getPlatform(): Platform = AndroidNativePlatform()
actual fun getPlatformName(): String = "AndroidNative"
actual fun getPlatformJson(): String = "{\"name\": \"${getPlatform().name}\"}"
