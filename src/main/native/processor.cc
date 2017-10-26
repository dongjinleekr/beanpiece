#include "com_dongjinlee_beanpiece_Processor.h"

#include "sentencepiece_processor.h"
#include "jni_utils.h"

sentencepiece::SentencePieceProcessor processor;

JNIEXPORT void JNICALL Java_com_dongjinlee_beanpiece_Processor_load
    (JNIEnv *env, jobject thiz, jstring jpath) {
    processor.LoadOrDie(jstring2string(env, jpath));
}

JNIEXPORT jobject JNICALL Java_com_dongjinlee_beanpiece_Processor_encodeToPieces
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
        jstring element = env->NewStringUTF(piece.c_str());
        env->CallBooleanMethod(ret, java_util_ArrayList_add, element);
        env->DeleteLocalRef(element);
    }

    return ret;
}

JNIEXPORT jobject JNICALL Java_com_dongjinlee_beanpiece_Processor_encodeToIds
    (JNIEnv *env, jobject thiz, jstring jstr) {
    std::string str = jstring2string(env, jstr);
    std::vector<int> ids;
    processor.Encode(str, &ids);

    jclass java_util_ArrayList = env->FindClass("java/util/ArrayList");
    jmethodID java_util_ArrayList_constructor = env->GetMethodID(java_util_ArrayList, "<init>", "(I)V");
    jmethodID java_util_ArrayList_add = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");

    jclass java_lang_Integer = env->FindClass("java/lang/Integer");
    jmethodID java_lang_Integer_constructor = env->GetMethodID(java_lang_Integer, "<init>", "(I)V");

    jobject ret = env->NewObject(java_util_ArrayList, java_util_ArrayList_constructor, ids.size());
    for (int& id: ids)
    {
        jobject element = env->NewObject(java_lang_Integer, java_lang_Integer_constructor, id);
        env->CallBooleanMethod(ret, java_util_ArrayList_add, element);
        env->DeleteLocalRef(element);
    }

    return ret;
}

JNIEXPORT jstring JNICALL Java_com_dongjinlee_beanpiece_Processor_decodePieces
    (JNIEnv *env, jobject thiz, jobject jobj) {
    std::vector<std::string> pieces = jstrlist2strvec(env, jobj);
    std::string decoded;
    processor.Decode(pieces, &decoded);
    jstring jstr = env->NewStringUTF(decoded.c_str());
    return jstr;
}

JNIEXPORT jstring JNICALL Java_com_dongjinlee_beanpiece_Processor_decodeIds
    (JNIEnv *env, jobject thiz, jobject jobj) {
    std::vector<int> ids = jintlist2intvec(env, jobj);
    std::string decoded;
    processor.Decode(ids, &decoded);
    jstring jstr = env->NewStringUTF(decoded.c_str());
    return jstr;
}
