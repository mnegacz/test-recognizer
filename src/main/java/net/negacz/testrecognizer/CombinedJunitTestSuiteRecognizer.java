package net.negacz.testrecognizer;

import net.negacz.testrecognizer.junit3.Junit3TestSuiteClassRecognizer;
import net.negacz.testrecognizer.junit4.Junit4TestSuiteClassRecognizer;
import org.objectweb.asm.ClassReader;

public class CombinedJunitTestSuiteRecognizer extends AbstractJunitTestSuiteRecognizer {

    private final AbstractJunitTestSuiteRecognizer junit3Recognizer;
    private final AbstractJunitTestSuiteRecognizer junit4Recognizer;

    public CombinedJunitTestSuiteRecognizer() {
        junit3Recognizer = new Junit3TestSuiteClassRecognizer();
        junit4Recognizer = new Junit4TestSuiteClassRecognizer();
    }

    @Override
    public boolean isTestClass(ClassReader classReader) {
        boolean isTestClass = junit3Recognizer.isTestClass(classReader);
        if (!isTestClass) {
            isTestClass = junit4Recognizer.isTestClass(classReader);
        }
        return  isTestClass;
    }

}
