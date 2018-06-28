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

import com.dongjinlee.beanpiece.util.NativeUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Builds a new {@link Processor}.
 * <p>
 * TODO: support loading a model from InputStream(=std::ifstream).
 * TODO: support encodeOptions, decodeOptions, etc.
 */
public final class ProcessorBuilder {
    static {
        try {
            NativeUtils.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@link Path} to SentencePiece model file.
     */
    private Path model;

    /**
     * Sets the location to the SentencePiece model file.
     *
     * @param model {@link Path} to the SentencePiece model file
     * @return {@link ProcessorBuilder} for build the processor
     */
    public ProcessorBuilder setModel(Path model) {
        this.model = model;
        return this;
    }

    /**
     * Returns a newly-created {@link Processor} based on the configuration properties set so far.
     *
     * @return {@link Processor} instance with the properties specified so far
     */
    public Processor build() {
        Objects.requireNonNull(model, "model");
        return build(model.toAbsolutePath().toString());
    }

    /**
     * Creates {@link Processor} instance with given parameters.
     */
    private native Processor build(String model);
}
