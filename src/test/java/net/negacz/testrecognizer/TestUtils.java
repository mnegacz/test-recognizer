package net.negacz.testrecognizer;

import java.io.IOException;

import static com.google.common.io.ByteStreams.toByteArray;
import static net.negacz.testrecognizer.utils.ClassUtils.CLASS_ENDING;
import static net.negacz.testrecognizer.utils.ClassUtils.getInputStream;

public class TestUtils {

    public static byte[] getBytecode(Class<?> clazz) throws IOException {
        return toByteArray(getInputStream(clazz));
    }

    public static String getFilePath(Class<?> clazz) {
        return clazz.getResource(clazz.getSimpleName() + CLASS_ENDING).getPath();
    }

}
