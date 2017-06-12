LOCAL_PATH := $(call my-dir)



include $(CLEAR_VARS)



LOCAL_MODULE := libcalculatestats

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := calculatectats.c



include $(BUILD_SHARED_LIBRARY)


