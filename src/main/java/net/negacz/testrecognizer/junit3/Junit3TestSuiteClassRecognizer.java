package net.negacz.testrecognizer.junit3;

import net.negacz.testrecognizer.AbstractJunitTestSuiteRecognizer;
import org.objectweb.asm.ClassReader;

import static net.negacz.testrecognizer.utils.OpcodeUtils.isNotSynthetic;

public class Junit3TestSuiteClassRecognizer extends AbstractJunitTestSuiteRecognizer {

    private static final String JUNIT_FRAMEWORK_TEST_CLASS_NAME = "junit/framework/TestCase";

    @Override
    public boolean isTestClass(ClassReader classReader) {
        return isNotSynthetic(classReader.getAccess()) && JUNIT_FRAMEWORK_TEST_CLASS_NAME.equals(classReader.getSuperName());
    }

}
