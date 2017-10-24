#include "com_github_dongjinleekr_beanpiece_Processor.h"

#include <iostream>
#include "sentencepiece_processor.h"
#include "jni_utils.h"

sentencepiece::SentencePieceProcessor processor;

JNIEXPORT void JNICALL Java_com_github_dongjinleekr_beanpiece_Processor_load
    (JNIEnv *env, jobject thiz, jstring jpath) {
    processor.LoadOrDie(jstring2string(env, jpath));
}

JNIEXPORT jobject JNICALL Java_com_github_dongjinleekr_beanpiece_Processor_encode
    (JNIEnv *env, jobject thiz, jstring jstr) {
    std::string str = jstring2string(env, jstr);
    std::vector<std::string> pieces;
    processor.Encode(str, &pieces);

    jclass java_util_ArrayList = env->FindClass("java/util/ArrayList");
    jmethodID java_util_ArrayList_constructor = env->GetMethodID(java_util_ArrayList, "<init>", "(I)V");
    jmethodID java_util_ArrayList_add = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");

    jobject ret = env->NewObject(java_util_ArrayList, java_util_ArrayList_constructor, pieces.size());
    for (std::string& piece: pieces)
    {
       jstring element = env -> NewStringUTF(piece.c_str());
       env->CallBooleanMethod(ret, java_util_ArrayList_add, element);
       env->DeleteLocalRef(element);
    }

    return ret;
}
