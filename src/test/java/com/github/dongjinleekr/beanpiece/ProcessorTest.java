package com.github.dongjinleekr.beanpiece;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@RunWith(JUnit4.class)
public class ProcessorTest {
    @Test
    public void test() {
        ClassLoader classLoader = getClass().getClassLoader();
        File modelFile = new File(classLoader.getResource("test_model.model").getFile());
        Processor processor = new Processor();
        processor.load(modelFile);
        List<String> tokens = processor.encode("This is a test.");
        assertThat(tokens, contains("▁This", "▁is", "▁a", "▁", "t", "est", "."));
    }
}
