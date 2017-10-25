#include "jni_utils.h"

std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

std::vector<std::string> jstrlist2strvec(JNIEnv *env, jobject list) {
    std::vector<std::string> ret;
    const jclass java_util_List = env->FindClass("java/util/List");
    const jmethodID java_util_List_get = env->GetMethodID(java_util_List, "get", "(I)Ljava/lang/Object;");
    const jmethodID java_util_List_size = env->GetMethodID(java_util_List, "size", "()I");
    jint size = env->CallIntMethod(list, java_util_List_size);
    for (int i = 0; i < size; ++i)
    {
        jobject element = env->CallObjectMethod(list, java_util_List_get, i);
        std::string str = jstring2string(env, (jstring)element);
        ret.push_back(str);
        env->DeleteLocalRef(element);
    }
    return ret;
}

std::vector<int> jintlist2intvec(JNIEnv *env, jobject list) {
    std::vector<int> ret;
    const jclass java_util_List = env->FindClass("java/util/List");
    const jmethodID java_util_List_get = env->GetMethodID(java_util_List, "get", "(I)Ljava/lang/Object;");
    const jmethodID java_util_List_size = env->GetMethodID(java_util_List, "size", "()I");

    const jclass java_lang_Integer = env->FindClass("java/lang/Integer");
    const jmethodID java_lang_Integer_intValue = env->GetMethodID(java_lang_Integer, "intValue", "()I");

    jint size = env->CallIntMethod(list, java_util_List_size);
    for (int i = 0; i < size; ++i)
    {
        jobject element = env->CallObjectMethod(list, java_util_List_get, i);
        jint value = env->CallIntMethod(element, java_lang_Integer_intValue);
        ret.push_back((int)value);
        env->DeleteLocalRef(element);
    }
    return ret;
}
