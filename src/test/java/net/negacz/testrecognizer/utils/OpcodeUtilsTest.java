package net.negacz.testrecognizer.utils;

import org.junit.Test;

import static net.negacz.testrecognizer.utils.OpcodeUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.objectweb.asm.Opcodes.*;

public class OpcodeUtilsTest {

    @Test
    public void shouldIsReturnTrueWhenOpcodesAreEqual() {
        // given
        int firstOpcode = ACC_ABSTRACT;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = is(firstOpcode, secondOpcode);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldIsReturnTrueWhenOpcodesAreOverlapping() {
        // given
        int firstOpcode = ACC_ABSTRACT | ACC_INTERFACE;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = is(firstOpcode, secondOpcode);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldIsReturnFalseWhenOpcodesAreDifferent() {
        // given
        int firstOpcode = ACC_INTERFACE;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = is(firstOpcode, secondOpcode);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldIsNotReturnFalseWhenOpcodesAreEqual() {
        // given
        int firstOpcode = ACC_ABSTRACT;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = isNot(firstOpcode, secondOpcode);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldIsNotReturnTrueWhenOpcodesAreDifferent() {
        // given
        int firstOpcode = ACC_INTERFACE;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = isNot(firstOpcode, secondOpcode);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldIsNotReturnFalseWhenOpcodesAreOverlapping() {
        // given
        int firstOpcode = ACC_ABSTRACT | ACC_INTERFACE;
        int secondOpcode = ACC_ABSTRACT;

        // when
        boolean result = isNot(firstOpcode, secondOpcode);

        // then
        assertFalse(result);
    }

    @Test
    public void isNotSyntheticReturnFalseWhenSynthetic() {
        // given
        int opcode = ACC_SYNTHETIC;

        // when
        boolean result = isNotSynthetic(opcode);

        // then
        assertFalse(result);
    }

    @Test
    public void isNotBridgeReturnFalseWhenBridge() {
        // given
        int opcode = ACC_BRIDGE;

        // when
        boolean result = isNotBridge(opcode);

        // then
        assertFalse(result);
    }

}
