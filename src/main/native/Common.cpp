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
#include "ProcessorBuilder.h"

ClassRegistry gClasses;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void*) {
  LOG_INFO("Initializing JNI");
  JNIEnv *env = jniHelpersInitialize(jvm);
  if (env == NULL) {
    return -1;
  }

  gClasses.add(env, new Processor(env));
  gClasses.add(env, new ProcessorBuilder(env));

  LOG_INFO("Initialization complete");
  return JAVA_VERSION;
}
