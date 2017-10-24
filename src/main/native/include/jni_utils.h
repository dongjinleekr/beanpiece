#include <jni.h>

#ifndef _Included_jni_utils
#define _Included_jni_utils

#include <string>

std::string jstring2string(JNIEnv *env, jstring jStr);

#endif
