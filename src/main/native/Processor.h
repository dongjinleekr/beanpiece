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

#ifndef __Processor_h__
#define __Processor_h__

#include "sentencepiece_processor.h"
#include "JniHelpers.h"
#include "Common.h"

using namespace spotify::jni;
using namespace sentencepiece;

class Processor : public NativeObject {
public:
  Processor();
  Processor(JNIEnv *env);
  ~Processor() {
    if (NULL != spp) {
      delete spp;
    }
  }

  const char* getCanonicalName() const {
    return MAKE_CANONICAL_NAME(PACKAGE, Processor);
  }

  void initialize(JNIEnv *env);
  void mapFields() {}

  bool load(const std::string& model);
  std::vector<std::string> encodeToPieces(const std::string& str);
  std::vector<int> encodeToIds(const std::string& str);
  std::string decodePieces(const std::vector<std::string>& pieces);
  std::string decodeIds(const std::vector<int>& ids);

  static jobjectArray nativeDoEncodeToPieces(JNIEnv *env, jobject java_this, jobject java_string);
  static jintArray nativeDoEncodeToIds(JNIEnv *env, jobject java_this, jobject java_string);
  static jstring nativeDoDecodePieces(JNIEnv *env, jobject java_this, jobjectArray java_string_array);
  static jstring nativeDoDecodeIds(JNIEnv *env, jobject java_this, jintArray java_int_array);
  static void nativeDestroy(JNIEnv *env, jobject java_this);

private:
  SentencePieceProcessor* spp;
};

#endif // __Processor_h__
