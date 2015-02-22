package net.negacz.testrecognizer;

import java.io.InputStream;
import java.util.Set;

public interface IInheritedJunitTestSuiteClassRecognizer {

    Set<Class<?>> extractTestClassesFromClasses(Set<Class<?>> classes);

    Set<String> extractTestClassesFromFilePaths(Set<String> classFilePaths);

    Set<InputStream> extractTestClassesFromInputStreams(Set<InputStream> classInputStreams);

    Set<byte[]> extractTestClassesFromBytecodes(Set<byte[]> bytecodes);

}
