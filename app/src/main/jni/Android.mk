LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/include

LOCAL_SRC_FILES := com_wang_demo4any_jni_NativeWrapper.cpp

LOCAL_LDLIBS := -lz -llog
LOCAL_MODULE := libtest_jni

include $(BUILD_SHARED_LIBRARY)