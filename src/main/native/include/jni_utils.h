#include <jni.h>

#ifndef _Included_jni_utils
#define _Included_jni_utils

#include <string>
#include <vector>

std::string jstring2string(JNIEnv *env, jstring jStr);

std::vector<std::string> jstrlist2strvec(JNIEnv *env, jobject list);

std::vector<int> jintlist2intvec(JNIEnv *env, jobject list);

#endif
