package tech.takenoko.kmmlib

class ArrWrapper {
    val platform: String get() = getPlatform().name
    val platformName: String get() = getPlatformName()
    val platformJson: String get() = getPlatformJson()
}
