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

import com.spotify.jni.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Facade to sentencepiece::SentencePieceProcessor.
 */
public final class Processor extends NativeObject implements AutoCloseable {
    private Processor() {
    }

    /**
     * Encodes given string into pieces. Correspond to <tt>SentencePieceProcessor::Encode(const std::string &amp;input, std::vector&lt;std::string&gt; *pieces)</tt>.
     *
     * @param str {@link String} to encode.
     * @return List of pieces.
     */
    public List<String> encodeToPieces(String str) {
        return Arrays.asList(doEncodeToPieces(str));
    }

    /**
     * Encodes given string into ids. Correspond to <tt>SentencePieceProcessor::Encode(const std::string &amp;input, std::vector&lt;int&gt; *ids)</tt>.
     *
     * @param str {@link String} to encode.
     * @return List of piece's ids.
     */
    public List<Integer> encodeToIds(String str) {
        List<Integer> result = new ArrayList<>();
        for (int i: doEncodeToIds(str)) {
            result.add(new Integer(i));
        }
        return result;
    }

    /**
     * Decodes given string pieces into string. Correspond to <tt>SentencePieceProcessor::Decode(const std::vector&lt;std::string&gt; &amp;pieces, std::string *detokenized)</tt>.
     *
     * @param pieces pieces to decode.
     * @return decoded string.
     */
    public String decodePieces(List<String> pieces) {
        String[] array = new String[pieces.size()];
        for (int i = 0; i < pieces.size(); ++i) {
            array[i] = pieces.get(i);
        }
        return doDecodePieces(array);
    }

    /**
     * Decodes given string pieces into string. Correspond to <tt>SentencePieceProcessor::Decode(const std::vector&lt;std::string&gt; &amp;pieces, std::string *detokenized)</tt>.
     *
     * @param pieces pieces to decode.
     * @return decoded string.
     */
    public String decodePieces(String... pieces) {
        return doDecodePieces(pieces);
    }

    /**
     * Decodes given piece's ids into string. Correspond to <tt>SentencePieceProcessor::Decode(const std::vector&lt;int&gt; &amp;ids, std::string *detokenized)</tt>.
     *
     * @param ids piece's ids to decode.
     * @return decoded string.
     */
    public String decodeIds(List<Integer> ids) {
        int[] array = new int[ids.size()];
        for (int i = 0; i < ids.size(); ++i) {
            array[i] = ids.get(i).intValue();
        }
        return doDecodeIds(array);
    }

    /**
     * Decodes given piece's ids into string. Correspond to <tt>SentencePieceProcessor::Decode(const std::vector&lt;int&gt; &amp;ids, std::string *detokenized)</tt>.
     *
     * @param ids piece's ids to decode.
     * @return decoded string.
     */
    public String decodeIds(int... ids) {
        return doDecodeIds(ids);
    }

    private native String[] doEncodeToPieces(String str);

    private native int[] doEncodeToIds(String str);

    private native String doDecodePieces(String[] pieces);

    private native String doDecodeIds(int[] ids);

    @Override
    public void close() {
        destroy();
    }

    @Override
    public native void destroy();
}
