#include <jni.h>
#include <string>
#include "libkmmlib_api.h"

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatform(JNIEnv *env,jobject thiz) {
    return (jstring) "";
}

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatformName(JNIEnv *env, jobject thiz) {
    libkmmlib_ExportedSymbols *symbols = libkmmlib_symbols();
    const char *buf = symbols->kotlin.root.tech.takenoko.kmmlib.getPlatformName();
    return env->NewStringUTF(buf);
}
extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatformJson(JNIEnv *env, jobject thiz) {
    libkmmlib_ExportedSymbols *symbols = libkmmlib_symbols();
    const char *buf = symbols->kotlin.root.tech.takenoko.kmmlib.getPlatformJson();
    return env->NewStringUTF(buf);
}