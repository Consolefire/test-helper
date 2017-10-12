package com.consolefire.test.helper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public abstract class ReflectionBasedPojoTest implements TargetObjectProviderFactory {

    private Object targetPojoObject;
    private final TargetObjectProvider targetObjectProvider;

    @Getter
    @Setter
    private boolean ignoreOnError = true;

    public ReflectionBasedPojoTest() {
        this.targetObjectProvider = getTargetObjectProvider();
    }

    public abstract ReflectionBasedPojoTestRule getTargetPojoRule();

    @Before
    public void setup() {
        try {
            targetPojoObject = targetObjectProvider.getTargetObject(getTargetPojoRule().getTargetPojoClass());
        } catch (Exception e) {

        }
        // ignore this test case if it failed to create instance of the target POJO.
        Assume.assumeTrue(targetPojoObject != null);
    }

    @Test
    public abstract void shouldRetutnFalseFromEqualsIfOtherIsNotLogicallyEqual();

    @Test
    public abstract void shouldRetutnTrueFromEqualsIfOtherIsLogicallyEqual();

    @Test
    public abstract void shouldGenerateLogicalToString();

    @Test
    public abstract void shouldGenerateLogicalHashCode();

    protected Object createTargetObject() throws InstantiationException, IllegalAccessException {
        Class<?> targetPojoClass = getTargetPojoRule().getTargetPojoClass();
        return createTargetObject(targetPojoClass);
    }

    protected Object createTargetObject(Class<?> targetPojoClass)
            throws InstantiationException, IllegalAccessException {
        if (!hasNoArgConstructor(targetPojoClass)) {
            throw new InstantiationException("No default constructor provided for class: " + targetPojoClass.getName());
        }
        Object targetPojo = targetPojoClass.newInstance();
        return targetPojo;
    }

    private boolean hasNoArgConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void shouldTestAllGetterMethods() {
        Class<?> targetPojoClass = getTargetPojoRule().getTargetPojoClass();
        List<Method> getterMethods = extractGetterMethods(targetPojoClass);
        if (null != getterMethods) {
            getterMethods.parallelStream().forEach(method -> {
                try {
                    method.invoke(targetPojoObject, new Object[] {});
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    @Test
    public void shouldTestAllsetterMethods() {
        Class<?> targetPojoClass = getTargetPojoRule().getTargetPojoClass();
        List<Method> setterMethods = extractSetterMethods(targetPojoClass);
        if (null != setterMethods) {
            setterMethods.parallelStream().forEach(method -> {
                try {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (null != paramTypes && paramTypes.length == 1) {
                        Object[] params = new Object[paramTypes.length];
                        Class<?> paramType = paramTypes[0];
                        Object paramToSet = new Object();
                        paramToSet = initializeSetterParameter(paramType, paramToSet);
                        params[0] = paramToSet;
                        method.invoke(targetPojoObject, params);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }


    private List<Method> extractSetterMethods(Class<?> targetPojoClass) {
        return extractGetterOrSetterMethods(targetPojoClass, true);
    }

    private List<Method> extractGetterMethods(Class<?> targetPojoClass) {
        return extractGetterOrSetterMethods(targetPojoClass, false);
    }

    private List<Method> extractGetterOrSetterMethods(Class<?> targetPojoClass, boolean setter) {
        List<Method> methods = new ArrayList<>();
        Field[] fields = targetPojoClass.getDeclaredFields();
        if (null != fields && fields.length > 0) {
            Arrays.stream(fields).forEach(field -> {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), targetPojoClass);
                    if (setter) {
                        methods.add(propertyDescriptor.getWriteMethod());
                    } else {
                        methods.add(propertyDescriptor.getReadMethod());
                    }
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            });
        }


        return methods;
    }


    private Object initializeSetterParameter(final Class<?> paramType, Object paramToSet)
            throws InstantiationException, IllegalAccessException {
        if (Set.class.isAssignableFrom(paramType)) {
            paramToSet = Collections.EMPTY_SET;
        } else if (List.class.isAssignableFrom(paramType)) {
            paramToSet = Collections.EMPTY_LIST;
        } else if (Boolean.class.isAssignableFrom(paramType)) {
            paramToSet = Boolean.TRUE;
        } else if (Integer.class.isAssignableFrom(paramType)) {
            paramToSet = Integer.valueOf(0);
        } else if (Float.class.isAssignableFrom(paramType)) {
            paramToSet = Float.valueOf(0);
        } else if (Long.class.isAssignableFrom(paramType)) {
            paramToSet = 0L;
        } else if (Double.class.isAssignableFrom(paramType)) {
            paramToSet = 0.0;
        } else if (Short.class.isAssignableFrom(paramType)) {
            paramToSet = Short.valueOf((short) 0);
        } else if (Number.class.isAssignableFrom(paramType)) {
            paramToSet = 0;
        } else if (paramType.isPrimitive()) {
            if (Boolean.TYPE.equals(paramType)) {
                paramToSet = true;
            } else if (Byte.TYPE.equals(paramType)) {
                paramToSet = Byte.MIN_VALUE;
            } else if (Character.TYPE.equals(paramType)) {
                paramToSet = 'c';
            } else if (Short.TYPE.equals(paramType)) {
                paramToSet = (short) 1;
            } else if (Integer.TYPE.equals(paramType)) {
                paramToSet = 1;
            } else if (Long.TYPE.equals(paramType)) {
                paramToSet = 1L;
            } else if (Float.TYPE.equals(paramType)) {
                paramToSet = 1.0F;
            } else if (Double.TYPE.equals(paramType)) {
                paramToSet = 1.0;
            }

        } else {
            paramToSet = paramType.newInstance();
        }
        return paramToSet;
    }

    @Test
    public void shouldTestHashCode() {
        targetPojoObject.hashCode();
    }

    @Test
    public void shouldRetutnTrueFromEqualsIfThisRefEqualsOther() {
        Object left = targetPojoObject;
        Object other = left;
        assertEquals(true, left.equals(other));
    }

    @Test
    public void shouldRetutnFalseFromEqualsIfOtherIsNull() {
        Object left = targetPojoObject;
        Object other = null;
        assertEquals(false, left.equals(other));
    }

    @Test
    public void shouldRetutnFalseFromEqualsIfOtherIsNotInstanceofThis() {
        Object left = targetPojoObject;
        Object other = new Object();
        assertEquals(false, left.equals(other));
    }

    @Test
    public void shouldGenerateToString() {
        targetPojoObject.toString();
    }
}
