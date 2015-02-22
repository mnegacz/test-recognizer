package net.negacz.testrecognizer.inheritance;

import com.google.common.collect.ImmutableMap;
import net.negacz.testrecognizer.data.*;
import net.negacz.testrecognizer.junit4.Junit4TestSuiteClassRecognizer;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static net.negacz.testrecognizer.TestUtils.getBytecode;
import static net.negacz.testrecognizer.TestUtils.getFilePath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class InheritedJunitTestSuiteClassRecognizerTest {

    @Test
    public void shouldRecognizeClassWithTestAnnotation() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        Set<Class<?>> classes = of(ClassWithTestAnnotation.class, ClassWithoutTestAnnotation.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWithTestAnnotation.class), result);
    }

    @Test
    public void shouldRecognizeClassWithTestAnnotationByBytecode() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        byte[] classWithAnnotation = getBytecode(ClassWithTestAnnotation.class);
        byte[] classWithoutAnnotation = getBytecode(ClassWithoutTestAnnotation.class);
        Set<byte[]> bytecodes = of(classWithAnnotation, classWithoutAnnotation);

        // when
        Set<byte[]> result = recognizer.extractTestClassesFromBytecodes(bytecodes);

        // then
        assertEquals(of(classWithAnnotation), result);
    }

    @Test
    public void shouldRecognizeClassWithTestAnnotationByFilePath() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        String classWithAnnotation = getFilePath(ClassWithTestAnnotation.class);
        String classWithoutAnnotation = getFilePath(ClassWithoutTestAnnotation.class);
        Set<String> filePaths = of(classWithAnnotation, classWithoutAnnotation);

        // when
        Set<String> result = recognizer.extractTestClassesFromFilePaths(filePaths);

        // then
        assertEquals(of(classWithAnnotation), result);
    }

    @Test
    public void shouldRecognizeClassWhichExtendClassWithTestAnnotation() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        Set<Class<?>> classes = of(ClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotation.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotation.class), result);
    }

    @Test
    public void shouldRecognizeClassWhichExtendClassWithTestAnnotationFurtherInInheritance() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        Set<Class<?>> classes = of(ClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotationFurtherInInheritance.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotation.class, ClassWhichExtendClassWithTestAnnotationFurtherInInheritance.class), result);
    }

    @Test
    public void shouldRecognizeClassWithTestAnnotationWhichExtendClassWithoutTestAnnotation() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer(new Junit4TestSuiteClassRecognizer());
        Set<Class<?>> classes = of(ClassWithoutTestAnnotation.class, ClassWithTestAnnotationWhichExtendClassWithoutTestAnnotation.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWithTestAnnotationWhichExtendClassWithoutTestAnnotation.class), result);
    }

    @Test
    public void shouldRecognizeBothJunitVersionTestClasses() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer();
        Set<Class<?>> classes = of(ClassWithTestAnnotation.class, ClassWhichExtendTestCase.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWithTestAnnotation.class, ClassWhichExtendTestCase.class), result);
    }

    @Test
    public void shouldRecognizeClassWhichExtendTestCaseFurtherInInheritance() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer();
        Set<Class<?>> classes = of(ClassWhichExtendTestCase.class, ClassWhichExtendTestCaseFurtherInInheritance.class);

        // when
        Set<Class<?>> result = recognizer.extractTestClassesFromClasses(classes);

        // then
        assertEquals(of(ClassWhichExtendTestCase.class, ClassWhichExtendTestCaseFurtherInInheritance.class), result);
    }

    @Test
    public void shouldReturnTrueWhenParentIsTestClass() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer();
        String className = "className";
        String parentClassName = "parentClassName";

        ClassCharacteristic characteristic = mock(ClassCharacteristic.class);
        when(characteristic.getParentClassName()).thenReturn(parentClassName);
        Set<String> testClasses = newHashSet(parentClassName);

        Map<String, ClassCharacteristic> characteristics = ImmutableMap.of(className, characteristic);

        // when
        boolean result = recognizer.anyOfParentsIsTestClass(className, characteristics, testClasses);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldNotReturnTrueWhenParentIsTestClass() throws Exception {
        // given
        InheritedJunitTestSuiteClassRecognizer recognizer = new InheritedJunitTestSuiteClassRecognizer();
        String className = "className";
        String parentClassName = "parentClassName";

        ClassCharacteristic characteristic = mock(ClassCharacteristic.class);
        when(characteristic.getParentClassName()).thenReturn(parentClassName);
        Set<String> testClasses = emptySet();

        Map<String, ClassCharacteristic> characteristics = ImmutableMap.of(className, characteristic);

        // when
        boolean result = recognizer.anyOfParentsIsTestClass(className, characteristics, testClasses);

        // then
        assertFalse(result);
    }

}