package net.negacz.testrecognizer.data;

import org.junit.Test;

public class ClassWithTestAnnotationWhichExtendClassWithoutTestAnnotation extends ClassWithoutTestAnnotation {

    @Test
    public void test() {}

}
