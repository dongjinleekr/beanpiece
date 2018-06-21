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

package com.dongjinlee.beanpiece;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

@RunWith(JUnit4.class)
public class ProcessorTest {
    @Test
    public void test() {
        ClassLoader classLoader = getClass().getClassLoader();
        File modelFile = new File(classLoader.getResource("test_model.model").getFile());
        Processor processor = new Processor();
        System.out.println(processor.ptr());

        /*processor.load(modelFile);

        // Test Processor#encodeToPieces
        List<String> pieces = processor.encodeToPieces("This is a test.");
        assertThat(pieces, contains("▁This", "▁is", "▁a", "▁", "t", "est", "."));

        // Test Processor#encodeToIds
        List<Integer> ids = processor.encodeToIds("This is a test.");
        assertThat(ids, contains(284, 47, 11, 4, 15, 400, 6));

        // Test Processor#decodePieces
        assertThat(processor.decodePieces(pieces), equalTo("This is a test."));

        // Test Processor#decodeIds
        assertThat(processor.decodeIds(ids), equalTo("This is a test."));*/
    }
}
