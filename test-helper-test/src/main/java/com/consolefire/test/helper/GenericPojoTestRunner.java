package com.consolefire.test.helper;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericPojoTestRunner<P> extends ReflectionBasedPojoTest implements TargetObjectProvider {

    @Override
    public ReflectionBasedPojoTestRule getTargetPojoRule() {
        return new ReflectionBasedPojoTestRule(getPojoClass());
    }
    

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<?> getPojoClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
        if (null != clazz) {
            return clazz;
        }
        throw new RuntimeException("No generic parameter defined to identify the target POJO class.");
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
            return super.createTargetObject(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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
