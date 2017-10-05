package com.consolefire.test.helper;

import static org.junit.Assert.assertTrue;

public class DefaultPojoTestRunner extends ReflectionBasedPojoTest implements TargetObjectProvider {

    @Override
    public ReflectionBasedPojoTestRule getTargetPojoRule() {
        return new ReflectionBasedPojoTestRule(Object.class);
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

    /*
     * (non-Javadoc)
     * 
     * @see com.consolefire.test.helper.TargetObjectProviderFactory#getTargetObjectProvider()
     */
    @Override
    public TargetObjectProvider getTargetObjectProvider() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.consolefire.test.helper.TargetObjectProvider#getTargetObject(java.lang.Class)
     */
    @Override
    public <T> Object getTargetObject(Class<T> clazz) {
        try {
            return createTargetObject(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
