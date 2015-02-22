package net.negacz.testrecognizer.junit3;

import net.negacz.testrecognizer.data.ClassWhichDoesntExtendTestCase;
import net.negacz.testrecognizer.data.ClassWhichExtendTestCase;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Junit3TestSuiteClassRecognizerTest {

    @Test
    public void shouldRecognizeClassWhichExtendsTestCase() throws IOException {
        // given
        Junit3TestSuiteClassRecognizer recognizer = new Junit3TestSuiteClassRecognizer();
        Class<?> clazz = ClassWhichExtendTestCase.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldNotRecognizeClassWhichDoesntExtendTestCase() throws IOException {
        // given
        Junit3TestSuiteClassRecognizer recognizer = new Junit3TestSuiteClassRecognizer();
        Class<?> clazz = ClassWhichDoesntExtendTestCase.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertFalse(isTestClass);
    }

}