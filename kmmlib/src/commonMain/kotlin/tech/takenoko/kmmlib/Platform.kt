@file:Suppress("NO_ACTUAL_FOR_EXPECT")
package tech.takenoko.kmmlib

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getPlatformName(): String
expect fun getPlatformJson(): String
