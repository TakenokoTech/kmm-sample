package tech.takenoko.kmmlib

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AndroidArrPlatform(
    override val name: String
): Platform {
    constructor(): this(name = "AndroidArr")
}

actual fun getPlatform(): Platform = AndroidArrPlatform()
actual fun getPlatformName(): String = "AndroidArr"
actual fun getPlatformJson(): String = Json.encodeToString(AndroidArrPlatform())
