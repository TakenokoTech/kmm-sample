#include <jni.h>
#include <string>
#include "libkmmlib_api.h"

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_getPlatform(JNIEnv *env,jobject thiz) {
    libkmmlib_ExportedSymbols *symbols = libkmmlib_symbols();
    libkmmlib_kref_tech_takenoko_kmmlib_Platform a = symbols->kotlin.root.tech.takenoko.kmmlib.getPlatform();
    return (jstring) "";
}

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_getPlatformName(JNIEnv *env, jobject thiz) {
    libkmmlib_ExportedSymbols *symbols = libkmmlib_symbols();
    const char *buf = symbols->kotlin.root.tech.takenoko.kmmlib.getPlatformName();
    return env->NewStringUTF(buf);
}