#include <jni.h>
#include <string>
#include "libnkmmlib_api.h"

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatform(JNIEnv *env,jobject thiz) {
    return (jstring) "";
}

extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatformName(JNIEnv *env, jobject thiz) {
    libnkmmlib_ExportedSymbols *symbols = libnkmmlib_symbols();
    const char *buf = symbols->kotlin.root.tech.takenoko.nkmmlib.getPlatformName();
    return env->NewStringUTF(buf);
}
extern "C" JNIEXPORT jstring JNICALL
Java_tech_takenoko_kmmlib_NdkWrapper_wrapPlatformJson(JNIEnv *env, jobject thiz) {
    libnkmmlib_ExportedSymbols *symbols = libnkmmlib_symbols();
    const char *buf = symbols->kotlin.root.tech.takenoko.nkmmlib.getPlatformJson();
    return env->NewStringUTF(buf);
}