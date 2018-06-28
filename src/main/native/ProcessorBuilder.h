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

#ifndef __ProcessorBuilder_h__
#define __ProcessorBuilder_h__

#include "JniHelpers.h"
#include "Common.h"

using namespace spotify::jni;

class ProcessorBuilder : public JavaClass {
public:
  ProcessorBuilder() : JavaClass() {}
  ProcessorBuilder(JNIEnv *env) : JavaClass(env) { initialize(env); }
  ~ProcessorBuilder() {}

  const char* getCanonicalName() const {
    return MAKE_CANONICAL_NAME(PACKAGE, ProcessorBuilder);
  }
  void initialize(JNIEnv *env);
  void mapFields() {}

private:
  static jobject buildProcessor(JNIEnv *env, jobject java_this, jobject java_string);
};

#endif // __ProcessorBuilder_h__
