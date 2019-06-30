#include <stdio.h>
#include <assert.h>
#include <limits.h>
#include <unistd.h>
#include <fcntl.h>
#include <android/log.h>
#include "jni.h"

#define LOG_TAG    "com_wang_demo4any_jni_NativeWrapper" // 这个是自定义的LOG的标识
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL, LOG_TAG, __VA_ARGS__)


struct fields_t {
    jfieldID context;
    jmethodID callback;
};
static fields_t fields;

static void com_wang_demo4any_jni_NativeWrapper_native_init(JNIEnv *env) {
    LOGD("native_init is invoked.");
    jclass clazz = env->FindClass("com/wang/demo4any/jni/NativeWrapper");
    LOGE("ERROR: NativeWrapper native registration failed");
    if (clazz == NULL) {
        LOGE("ERROR: NativeWrapper native registration failed\n");
        return;
    }

    fields.context = env->GetFieldID(clazz, "mNativeContext", "I");
    if (fields.context == NULL) {
        LOGE("ERROR: Can't find NativeWrapper.mNativeContext\n");
        return;
    }

    fields.callback = env->GetMethodID(clazz, "callback", "()V");
    if (fields.callback == NULL) {
        LOGE("ERROR: Can't find NativeWrapper.callback\n");
        return;
    }
}

static void com_wang_demo4any_jni_NativeWrapper_native_setup(JNIEnv *env, jobject thiz)  {
    LOGD("native_setup is invoked.");
    env->CallVoidMethod(thiz, fields.callback);
}

static JNINativeMethod gMethods[] = {
        {"native_init",     "()V",    (void *)com_wang_demo4any_jni_NativeWrapper_native_init},
        {"native_setup",    "()V",    (void *)com_wang_demo4any_jni_NativeWrapper_native_setup},
};

static int register_com_wang_demo4any_jni_NativeWrapper(JNIEnv *env) {
    jclass clazz = env->FindClass("com/wang/demo4any/jni/NativeWrapper");
    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods)/ sizeof(JNINativeMethod)) < 0) {
        return -1;
    }
    return 0;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    LOGI("JNI_OnLoad is invoked.");
    JNIEnv* env = NULL;
    jint result = -1;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: MediaPlayer native registration failed\n");
        goto bail;
    }
    assert(env != NULL);
    if (register_com_wang_demo4any_jni_NativeWrapper(env) < 0) {
        LOGE("ERROR: MediaPlayer native registration failed\n");
        goto bail;
    }
    result = JNI_VERSION_1_4;

    bail:
    return result;
}
