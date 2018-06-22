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

package com.dongjinlee.beanpiece.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

/**
 * Loads shared object contained in the jar through temp directory.
 * <p>
 * see: https://github.com/facebook/rocksdb/blob/master/java/src/main/java/org/rocksdb/util/Environment.java
 * see: https://github.com/facebook/rocksdb/blob/master/java/src/main/java/org/rocksdb/NativeLibraryLoader.java
 */
public final class NativeUtils {

    private static OS os() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        if (os.contains("win")) {
            return OS.WINDOWS;
        } else if (os.contains("mac")) {
            return OS.OSX;
        } else if (os.contains("nix") || os.contains("nux")) {
            return OS.LINUX;
        } else {
            throw new IllegalArgumentException("Unsupported os: " + os);
        }
    }

    private static Architecture architecture() {
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);

        if (arch.contains("ppc")) {
            return Architecture.PPC;
        } else if (arch.contains("i386") || arch.contains("x86")) {
            return Architecture.I386;
        } else if (arch.contains("amd64") || arch.contains("x86_64")) {
            return Architecture.AMD64;
        } else {
            throw new IllegalArgumentException("Unsupported architecture: " + arch);
        }
    }

    private static String extension() {
        switch (os()) {
            case WINDOWS:
                return "dll";
            case OSX:
                return "dylib";
            default:
                return "so";
        }
    }

    private static String path(final String library) {
        return "/" + os().name().toLowerCase(Locale.ENGLISH)
                + "/" + architecture().name().toLowerCase(Locale.ENGLISH) + "/lib" + library + "." + extension();
    }

    /**
     * Loads specified library, regarding underlying system (i.e., os and architecture.)
     */
    public static synchronized void load(final String library) throws IOException {
        String path = path(library);
        InputStream resource = NativeUtils.class.getResourceAsStream(path);
        Path tempPath = Files.createTempFile("lib" + library, "." + extension());
        Files.copy(resource, tempPath, StandardCopyOption.REPLACE_EXISTING);
        System.load(tempPath.toString());
    }

    /**
     * Loads all required libraries at once.
     */
    public static void load() throws IOException {
        load("sentencepiece");
        load("beanpiece");
    }

    private NativeUtils() {
    }
}
