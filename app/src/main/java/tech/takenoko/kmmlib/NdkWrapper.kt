package tech.takenoko.kmmlib

class NdkWrapper {

    init { System.loadLibrary("ndklib") }

    private external fun wrapPlatform(): String
    private external fun wrapPlatformName(): String
    private external fun wrapPlatformJson(): String

    val platform: String get() = wrapPlatform()
    val platformName: String get() = wrapPlatformName()
    val platformJson: String get() = wrapPlatformJson()
}
