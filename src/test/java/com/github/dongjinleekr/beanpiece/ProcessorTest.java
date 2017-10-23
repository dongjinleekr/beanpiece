package com.github.dongjinleekr.beanpiece;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(JUnit4.class)
public class ProcessorTest {
    @Test
    public void test() {
        Processor processor = new Processor();
        assertThat(processor.getString(), equalTo("HELLO SENTENCEPIECE!!"));
    }
}
