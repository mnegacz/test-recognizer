package net.negacz.testrecognizer;

import org.objectweb.asm.ClassReader;

import java.io.InputStream;

import static net.negacz.testrecognizer.utils.ClassUtils.*;

public abstract class AbstractJunitTestSuiteRecognizer implements IJunitTestSuiteClassRecognizer {

    public boolean isTestClass(Class<?> clazz) {
        return isTestClass(getInputStream(clazz));
    }

    public boolean isTestClass(String filePath) {
        return isTestClass(getFileInputStream(filePath));
    }

    public boolean isTestClass(InputStream inputStream) {
        return isTestClass(getClassReader(inputStream));
    }

    public boolean isTestClass(byte[] bytecode) {
        return isTestClass(new ClassReader(bytecode));
    }

    public abstract boolean isTestClass(ClassReader classReader);

}