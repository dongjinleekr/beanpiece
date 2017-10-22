package com.github.dongjinleekr.beanpiece;

public class Processor {
    static {
        System.loadLibrary("beanpiece");
    }

    public native String getString();
}
