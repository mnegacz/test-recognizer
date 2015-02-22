package net.negacz.testrecognizer.junit4;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static net.negacz.testrecognizer.utils.OpcodeUtils.isNotBridge;
import static net.negacz.testrecognizer.utils.OpcodeUtils.isNotSynthetic;
import static org.objectweb.asm.Opcodes.ASM5;

class Junit4TestClassVisitor extends ClassVisitor {

    private final Junit4TestMethodVisitor methodVisitor;

    public Junit4TestClassVisitor() {
        super(ASM5);
        methodVisitor = new Junit4TestMethodVisitor();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        super.visitMethod(access, name, desc, signature, exceptions);
        if (isNotSynthetic(access) && isNotBridge(access) && !methodVisitor.isJunitTestAnnotationPresent()) {
            return methodVisitor;
        }
        return null;
    }

    public boolean hasAnyTestMethod() {
        return methodVisitor.isJunitTestAnnotationPresent();
    }

}
