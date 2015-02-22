package net.negacz.testrecognizer.utils;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

public class OpcodeUtils {

    public static boolean is(int value, int opcode) {
        return (value & opcode) != 0;
    }

    public static boolean isNot(int value, int opcode) {
        return !is(value, opcode);
    }

    public static boolean isNotSynthetic(int value) {
        return isNot(value, ACC_SYNTHETIC);
    }

    public static boolean isNotBridge(int value) {
        return isNot(value, ACC_BRIDGE);
    }

}
