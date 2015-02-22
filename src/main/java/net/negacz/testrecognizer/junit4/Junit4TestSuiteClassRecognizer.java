package net.negacz.testrecognizer.junit4;

import net.negacz.testrecognizer.AbstractJunitTestSuiteRecognizer;
import org.objectweb.asm.ClassReader;

import static net.negacz.testrecognizer.utils.OpcodeUtils.isNotSynthetic;
import static org.objectweb.asm.ClassReader.*;

public class Junit4TestSuiteClassRecognizer extends AbstractJunitTestSuiteRecognizer {

    @Override
    public boolean isTestClass(ClassReader classReader) {
        if (isNotSynthetic(classReader.getAccess())) {
            Junit4TestClassVisitor classVisitor = new Junit4TestClassVisitor();
            classReader.accept(classVisitor, SKIP_CODE | SKIP_DEBUG | SKIP_FRAMES);
            return classVisitor.hasAnyTestMethod();
        }
        return false;
    }

}
