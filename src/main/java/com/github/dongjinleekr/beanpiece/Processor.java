package com.github.dongjinleekr.beanpiece;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * The facade to sentencepiece::SentencePieceProcessor.
 */
public class Processor {
    static {
        System.loadLibrary("beanpiece");
    }

    public void load(File file) throws IllegalStateException {
        load(file.toPath());
    }

    public void load(Path path) throws IllegalStateException {
        load(path.toAbsolutePath().toString());
    }

    private native void load(String path) throws IllegalStateException;

    public native List<String> encodeToPieces(String str);

    public native List<Integer> encodeToIds(String str);

    public native String decodePieces(List<String> pieces);

    public native String decodeIds(List<Integer> ids);
}
