package com.github.dongjinleekr.beanpiece;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@RunWith(JUnit4.class)
public class ProcessorTest {
    @Test
    public void test() {
        ClassLoader classLoader = getClass().getClassLoader();
        File modelFile = new File(classLoader.getResource("test_model.model").getFile());
        Processor processor = new Processor();
        processor.load(modelFile);

        // Test Processor#encodeToPieces
        List<String> pieces = processor.encodeToPieces("This is a test.");
        assertThat(pieces, contains("▁This", "▁is", "▁a", "▁", "t", "est", "."));

        // Test Processor#encodeToIds
        List<Integer> ids = processor.encodeToIds("This is a test.");
        assertThat(ids, contains(284, 47, 11, 4, 15, 400, 6));

        // Test Processor#decodePieces
        assertThat(processor.decodePieces(pieces), equalTo("This is a test."));

        // Test Processor#decodeIds
        assertThat(processor.decodeIds(ids), equalTo("This is a test."));
    }
}
