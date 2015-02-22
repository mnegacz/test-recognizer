package net.negacz.testrecognizer.junit4;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

class Junit4TestMethodVisitor extends MethodVisitor {

    private static final String JUNIT_TEST_ANNOTATION_CLASS_NAME = "Lorg/junit/Test;";

    private boolean junitTestAnnotationPresent;

    public Junit4TestMethodVisitor() {
        super(ASM5);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        super.visitAnnotation(desc, visible);
        if (JUNIT_TEST_ANNOTATION_CLASS_NAME.equals(desc)) {
            junitTestAnnotationPresent = true;
        }
        return null;
    }

    public boolean isJunitTestAnnotationPresent() {
        return junitTestAnnotationPresent;
    }

}
