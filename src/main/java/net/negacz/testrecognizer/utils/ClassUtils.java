package net.negacz.testrecognizer.utils;


import org.objectweb.asm.ClassReader;

import java.io.*;
import java.util.Collection;
import java.util.stream.Stream;

public class ClassUtils {

    public static final String CLASS_ENDING = ".class";

    public static InputStream getInputStream(Class<?> clazz) {
        return clazz.getResourceAsStream(clazz.getSimpleName() + CLASS_ENDING);
    }

    public static FileInputStream getFileInputStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static ClassReader getClassReader(InputStream inputStream) {
        try {
            return new ClassReader(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> Stream<T> stream(Collection<T> collection) {
        return collection.parallelStream();
    }

}
