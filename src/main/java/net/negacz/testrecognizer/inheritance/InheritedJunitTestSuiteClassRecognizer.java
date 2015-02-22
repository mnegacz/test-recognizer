package net.negacz.testrecognizer.inheritance;


import net.negacz.testrecognizer.AbstractJunitTestSuiteRecognizer;
import net.negacz.testrecognizer.CombinedJunitTestSuiteRecognizer;
import net.negacz.testrecognizer.IInheritedJunitTestSuiteClassRecognizer;
import net.negacz.testrecognizer.utils.ClassUtils;
import org.objectweb.asm.ClassReader;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class InheritedJunitTestSuiteClassRecognizer implements IInheritedJunitTestSuiteClassRecognizer {

    private final AbstractJunitTestSuiteRecognizer recognizer;

    public InheritedJunitTestSuiteClassRecognizer() {
        recognizer = new CombinedJunitTestSuiteRecognizer();
    }

    public InheritedJunitTestSuiteClassRecognizer(AbstractJunitTestSuiteRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    @Override
    public Set<Class<?>> extractTestClassesFromClasses(Set<Class<?>> classes) {
        return mapThenExtractAndMapBack(classes, ClassUtils::getInputStream, this::extractTestClassesFromInputStreams);
    }

    @Override
    public Set<String> extractTestClassesFromFilePaths(Set<String> filePaths) {
        return mapThenExtractAndMapBack(filePaths, ClassUtils::getFileInputStream, this::extractTestClassesFromInputStreams);
    }

    @Override
    public Set<InputStream> extractTestClassesFromInputStreams(Set<InputStream> inputStreams) {
        return mapThenExtractAndMapBack(inputStreams, ClassUtils::getClassReader, this::extractTestClassesFromClassReaders);
    }

    @Override
    public Set<byte[]> extractTestClassesFromBytecodes(Set<byte[]> bytecodes) {
        return mapThenExtractAndMapBack(bytecodes, ClassReader::new, this::extractTestClassesFromClassReaders);
    }

    <T, R> Set<T> mapThenExtractAndMapBack(Set<T> original, Function<T, R> mapper, Function<Set<R>, Set<R>> extractor) {
        Map<R, T> map = original.stream().collect(toMap(mapper::apply, identity()));
        Set<R> result = extractor.apply(map.keySet());
        return result.stream().map(map::get).collect(toSet());
    }

    Set<ClassReader> extractTestClassesFromClassReaders(Set<ClassReader> classReaders) {
        Map<String, ClassCharacteristic> characteristics = getClassCharacteristicMap(classReaders);
        return getClassReaders(characteristics, getTestClasses(characteristics));
    }

    private Map<String, ClassCharacteristic> getClassCharacteristicMap(Set<ClassReader> classReaders) {
        return classReaders.stream().collect(toMap(ClassReader::getClassName, this::getClassCharacteristic));
    }

    private ClassCharacteristic getClassCharacteristic(ClassReader classReader) {
        return new ClassCharacteristic(classReader, recognizer.isTestClass(classReader));
    }

    private Set<String> getTestClasses(Map<String, ClassCharacteristic> characteristics) {
        Set<String> testClassesCache = new HashSet<>();
        return characteristics.keySet().stream().filter(className -> anyOfParentsIsTestClass(className, characteristics, testClassesCache)).collect(toSet());
    }

    boolean anyOfParentsIsTestClass(String className, Map<String, ClassCharacteristic> characteristics, Set<String> testClassesCache) {
        if (testClassesCache.contains(className)) {
            return true;
        }

        if (characteristics.containsKey(className)) {
            ClassCharacteristic characteristic = characteristics.get(className);
            if (characteristic.isTestClass() || anyOfParentsIsTestClass(characteristic.getParentClassName(), characteristics, testClassesCache)) {
                testClassesCache.add(className);
                return true;
            }
        }
        return false;
    }

    private Set<ClassReader> getClassReaders(Map<String, ClassCharacteristic> characteristics, Set<String> testClasses) {
        return testClasses.stream().map(className -> characteristics.get(className).getClassReader()).collect(toSet());
    }

}
