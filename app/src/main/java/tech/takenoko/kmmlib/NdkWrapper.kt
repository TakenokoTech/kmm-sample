package tech.takenoko.kmmlib

class NdkWrapper {

    init { System.loadLibrary("ndklib") }

    external fun getPlatform(): String
    external fun getPlatformName(): String
}
