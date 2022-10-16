package tech.takenoko.kmmlib

//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json

data class IosPlatform(
    override val name: String
): Platform {
    constructor(): this("iOS")
}

actual fun getPlatform(): Platform = IosPlatform()
actual fun getPlatformName(): String = "iOS"
actual fun getPlatformJson(): String = "" // Json.encodeToString(getPlatform())
