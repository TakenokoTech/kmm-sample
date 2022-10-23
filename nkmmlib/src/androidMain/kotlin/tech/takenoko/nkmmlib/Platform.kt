package tech.takenoko.nkmmlib

data class AndroidNativePlatform(
    override val name: String
): Platform {
    constructor(): this("Android")
}

actual fun getPlatform(): Platform = TODO()
actual fun getPlatformName(): String = TODO()
actual fun getPlatformJson(): String = TODO()
