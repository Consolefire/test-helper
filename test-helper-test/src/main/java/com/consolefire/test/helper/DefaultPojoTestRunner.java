package com.consolefire.test.helper;

import static org.junit.Assert.assertTrue;

public class DefaultPojoTestRunner extends ReflectionBasedPojoTest {

    @Override
    public ReflectionBasedPojoTestRule getTargetPojoRule() {
        return new ReflectionBasedPojoTestRule(Void.class);
    }

    @Override
    public void shouldRetutnFalseFromEqualsIfOtherIsNotLogicallyEqual() {
        assertTrue(true);
    }

    @Override
    public void shouldRetutnTrueFromEqualsIfOtherIsLogicallyEqual() {
        assertTrue(true);
    }

    @Override
    public void shouldGenerateLogicalToString() {
        assertTrue(true);
    }

    @Override
    public void shouldGenerateLogicalHashCode() {
        assertTrue(true);
    }

}
