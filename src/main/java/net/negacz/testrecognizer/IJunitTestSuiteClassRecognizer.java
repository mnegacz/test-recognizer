package net.negacz.testrecognizer;

import java.io.InputStream;

@SuppressWarnings("WeakerAccess")
public interface IJunitTestSuiteClassRecognizer {

    boolean isTestClass(Class<?> clazz);

    boolean isTestClass(String filePath);

    boolean isTestClass(InputStream inputStream);

    boolean isTestClass(byte[] bytecode);

}
