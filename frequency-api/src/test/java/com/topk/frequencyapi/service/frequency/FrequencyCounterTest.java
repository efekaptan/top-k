package com.topk.frequencyapi.service.frequency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class FrequencyCounterTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public FrequencyCounter counter() {
            return new FrequencyCounterImpl();
        }
    }

    @Autowired
    private FrequencyCounter counter;

    @Test
    public void shouldIncrement() {
        counter.insert("test2");
        counter.insert("test1");
        assertEquals(Collections.singletonList("test1"), counter.getTopTerms(1));

        counter.insert("test1");
        assertEquals(Arrays.asList("test1", "test2"), counter.getTopTerms(2));

        counter.insert("test3");
        counter.insert("test3");
        assertEquals(Arrays.asList("test3", "test1"), counter.getTopTerms(2));
    }

    @Test
    public void shouldDecrement() {
        counter.insert("test1");
        counter.insert("test2");
        counter.remove("test2");
        assertEquals(Collections.singletonList("test1"), counter.getTopTerms(1));

        counter.remove("test1");
        assertEquals(Collections.emptyList(), counter.getTopTerms(1));
    }

    @Test
    public void shouldIncrementAndDecrement() {
        counter.insert("test1");
        counter.insert("test2");
        counter.insert("test2");
        assertEquals(Arrays.asList("test2", "test1"), counter.getTopTerms(2));
        assertEquals(Collections.singletonList("test2"), counter.getTopTerms(1));

        counter.insert("test3");
        counter.insert("test3");
        assertEquals(Collections.singletonList("test3"), counter.getTopTerms(1));

        counter.insert("test3");
        assertEquals(Arrays.asList("test3", "test2"), counter.getTopTerms(2));

        counter.remove("test3");
        assertEquals(Arrays.asList("test3", "test2"), counter.getTopTerms(2));

        counter.remove("test3");
        assertEquals(Arrays.asList("test2", "test3"), counter.getTopTerms(2));

        counter.remove("test3");
        assertEquals(Arrays.asList("test2", "test1"), counter.getTopTerms(2));

        counter.remove("test2");
        assertEquals(Arrays.asList("test2", "test1"), counter.getTopTerms(2));

        counter.remove("test1");
        assertEquals(Collections.singletonList("test2"), counter.getTopTerms(2));

        counter.remove("test2");
        assertEquals(Collections.emptyList(), counter.getTopTerms(2));
        assertEquals(0, counter.getTermCount());
    }
}
