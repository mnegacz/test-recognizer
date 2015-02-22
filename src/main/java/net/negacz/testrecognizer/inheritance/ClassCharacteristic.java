package net.negacz.testrecognizer.inheritance;

import org.objectweb.asm.ClassReader;

class ClassCharacteristic {

    private final ClassReader classReader;
    private final boolean testClass;

    public ClassCharacteristic(ClassReader classReader, boolean testClass) {
        this.classReader = classReader;
        this.testClass = testClass;
    }

    public ClassReader getClassReader() {
        return classReader;
    }

    public String getParentClassName() {
        return classReader.getSuperName();
    }

    public boolean isTestClass() {
        return testClass;
    }

}
