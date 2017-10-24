package com.github.dongjinleekr.beanpiece;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

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

    public native List<String> encode(String str);
}
