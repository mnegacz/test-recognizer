package net.negacz.testrecognizer.junit4;

import net.negacz.testrecognizer.data.ClassWithTestAnnotation;
import net.negacz.testrecognizer.data.ClassWithTwoMethodsAndOneTestAnnotation;
import net.negacz.testrecognizer.data.ClassWithTwoTestAnnotations;
import net.negacz.testrecognizer.data.ClassWithoutTestAnnotation;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static net.negacz.testrecognizer.TestUtils.getBytecode;
import static net.negacz.testrecognizer.TestUtils.getFilePath;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Junit4TestSuiteClassRecognizerTest {

    @Test
    public void shouldRecognizeClassWithTestAnnotation() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        Class<?> clazz = ClassWithTestAnnotation.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldRecognizeClassWithTestAnnotationByBytecode() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        byte[] bytecode = getBytecode(ClassWithTestAnnotation.class);

        // when
        boolean isTestClass = recognizer.isTestClass(bytecode);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldRecognizeClassWithTestAnnotationByFilePath() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        String filePath = getFilePath(ClassWithTestAnnotation.class);

        // when
        boolean isTestClass = recognizer.isTestClass(filePath);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldRecognizeClassWithTwoMethodsAndOneTestAnnotation() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        Class<?> clazz = ClassWithTwoMethodsAndOneTestAnnotation.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldRecognizeClassWithTwoTestAnnotations() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        Class<?> clazz = ClassWithTwoTestAnnotations.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertTrue(isTestClass);
    }

    @Test
    public void shouldNotRecognizeClassWithoutTestAnnotation() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        Class<?> clazz = ClassWithoutTestAnnotation.class;

        // when
        boolean isTestClass = recognizer.isTestClass(clazz);

        // then
        assertFalse(isTestClass);
    }

    @Test(expected = UncheckedIOException.class)
    public void shouldThrowUncheckedIOExceptionWhenClassFilePathIsInvalid() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        String filePath = "/fake/path";

        // when
        recognizer.isTestClass(filePath);

        // then
        // exception has been thrown
    }

    @Test(expected = UncheckedIOException.class)
    public void shouldThrowUncheckedIOExceptionWhenInputStreamThrowsException() throws IOException {
        // given
        Junit4TestSuiteClassRecognizer recognizer = new Junit4TestSuiteClassRecognizer();
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException());

        // when
        recognizer.isTestClass(inputStream);

        // then
        // exception has been thrown
    }

}