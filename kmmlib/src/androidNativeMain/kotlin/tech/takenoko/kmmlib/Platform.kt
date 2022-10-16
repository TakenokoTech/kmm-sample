package tech.takenoko.kmmlib

data class AndroidNativePlatform(
    override val name: String
): Platform {
    constructor(): this("AndroidNative")
}

actual fun getPlatform(): Platform = AndroidNativePlatform()
actual fun getPlatformName(): String = "AndroidNative"
actual fun getPlatformJson(): String = "{\"name\": \"${getPlatform().name}\"}"

expect fun getPlatform(): Platform
expect fun getPlatformName(): String
expect fun getPlatformJson(): String