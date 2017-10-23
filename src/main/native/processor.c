#include "com_github_dongjinleekr_beanpiece_Processor.h"
#include <stdio.h>

#define DEFAULT_RET "HELLO SENTENCEPIECE!!"

JNIEXPORT jstring JNICALL Java_com_github_dongjinleekr_beanpiece_Processor_getString
  (JNIEnv *env, jobject thiz) {
    char* ret = DEFAULT_RET;
    return (*env)->NewStringUTF(env, ret);
}