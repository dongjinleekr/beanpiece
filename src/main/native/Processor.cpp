/*
 * Copyright (c) 2018 Lee Dongjin
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

#include "Processor.h"

#include <iostream>

Processor::Processor() : NativeObject(), spp(NULL) {}

Processor::Processor(JNIEnv *env) : NativeObject(env), spp(NULL) {
  initialize(env);
}

void Processor::initialize(JNIEnv *env) {
  setClass(env);
  cacheConstructor(env);
#if ANDROID
  addNativeMethod("destroy", &Processor::nativeDestroy, kTypeVoid, NULL);
  addNativeMethod("doEncodeToPieces", &Processor::nativeDoEncodeToPieces, kTypeObjectArray(kTypeString), kTypeString, NULL);
  addNativeMethod("doEncodeToIds", &Processor::nativeDoEncodeToIds, kTypeArray(kTypeInt), kTypeString, NULL);
  addNativeMethod("doDecodePieces", &Processor::nativeDoDecodePieces, kTypeString, kTypeObjectArray(kTypeString), NULL);
  addNativeMethod("doDecodeIds", &Processor::nativeDoDecodeIds, kTypeString, kTypeArray(kTypeInt), NULL);
#else
  addNativeMethod("destroy", (void*)&Processor::nativeDestroy, kTypeVoid, NULL);
  addNativeMethod("doEncodeToPieces", (void*)&Processor::nativeDoEncodeToPieces, kTypeObjectArray(kTypeString), kTypeString, NULL);
  addNativeMethod("doEncodeToIds", (void*)&Processor::nativeDoEncodeToIds, kTypeArray(kTypeInt), kTypeString, NULL);
  addNativeMethod("doDecodePieces", (void*)&Processor::nativeDoDecodePieces, kTypeString, kTypeObjectArray(kTypeString), NULL);
  addNativeMethod("doDecodeIds", (void*)&Processor::nativeDoDecodeIds, kTypeString, kTypeArray(kTypeInt), NULL);
#endif
  registerNativeMethods(env);
}

bool Processor::load(const std::string& model) {
  spp = new SentencePieceProcessor();
  return spp->Load(model);
}

std::vector<std::string> Processor::encodeToPieces(const std::string& str) {
  std::vector<std::string> pieces;
  spp->Encode(str, &pieces);
  return pieces;
}

std::vector<int> Processor::encodeToIds(const std::string& str) {
  std::vector<int> ids;
  spp->Encode(str, &ids);
  return ids;
}

std::string Processor::decodePieces(const std::vector<std::string>& pieces) {
  std::string result;
  spp->Decode(pieces, &result);
  return result;
}

std::string Processor::decodeIds(const std::vector<int>& ids) {
  std::string result;
  spp->Decode(ids, &result);
  return result;
}

jobjectArray Processor::nativeDoEncodeToPieces(JNIEnv *env, jobject java_this, jobject java_string) {
  Processor *object = gClasses.getNativeInstance<Processor>(env, java_this);
  JavaString str(env, (jstring)java_string);
  const std::vector<std::string>& pieces = object->encodeToPieces(str.get());
  JavaString **data = (JavaString **)malloc(pieces.size() * sizeof(JavaString*));
  for (std::size_t i = 0; i != pieces.size(); ++i) {
    const std::string token = pieces[i];
    data[i] = new JavaString(token);
  }
  JavaStringArray javaStringArray(data, pieces.size(), true);
  JniLocalRef<jobjectArray> result = javaStringArray.toJavaStringArray(env);
  return result.leak();
}

jintArray Processor::nativeDoEncodeToIds(JNIEnv *env, jobject java_this, jobject java_string) {
  Processor *object = gClasses.getNativeInstance<Processor>(env, java_this);
  JavaString str(env, (jstring)java_string);
  const std::vector<int>& ids = object->encodeToIds(str.get());
  jint* idsArray = new jint[ids.size()];
  for(size_t i = 0; i < ids.size(); ++i) {
    idsArray[i] = (jint)ids[i];
  }
  jintArray intArray = env->NewIntArray(ids.size());
  if (nullptr == intArray) {
    return nullptr;
  }
  env->SetIntArrayRegion(intArray, 0, ids.size(), idsArray);
  delete idsArray;
  return intArray;
}

jstring Processor::nativeDoDecodePieces(JNIEnv *env, jobject java_this, jobjectArray java_string_array) {
  Processor *object = gClasses.getNativeInstance<Processor>(env, java_this);
  std::vector<std::string> pieces;
  JavaStringArray javaStringArray(env, java_string_array);
  for (size_t i = 0; i < javaStringArray.size(); ++i) {
    pieces.push_back(javaStringArray.get()[i]->get());
  }
  std::string decoded = object->decodePieces(pieces);
  JavaString javaString(decoded);
  return javaString.toJavaString(env).leak();
}

jstring Processor::nativeDoDecodeIds(JNIEnv *env, jobject java_this, jintArray java_int_array) {
  Processor *object = gClasses.getNativeInstance<Processor>(env, java_this);
  jint *jintArray = env->GetIntArrayElements(java_int_array, NULL);
  if (nullptr == jintArray) {
    return nullptr;
  }
  jsize length = env->GetArrayLength(java_int_array);
  std::vector<int> ids;
  for (jsize i = 0; i < length; ++i) {
    ids.push_back((int)jintArray[i]);
  }
  std::string decoded = object->decodeIds(ids);
  JavaString javaString(decoded);
  return javaString.toJavaString(env).leak();
}

void Processor::nativeDestroy(JNIEnv *env, jobject java_this) {
  Processor *object = gClasses.getNativeInstance<Processor>(env, java_this);
  if (object != NULL) {
    object->destroy(env, java_this);
  }
}
