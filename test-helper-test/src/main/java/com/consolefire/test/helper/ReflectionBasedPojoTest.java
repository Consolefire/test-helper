package com.consolefire.test.helper;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    protected Object createTargetObject(Class<?> targetPojoClass) throws InstantiationException, IllegalAccessException {
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
    public void shouldTestAllGetterMethods() throws InstantiationException, IllegalAccessException {
        Class<?> targetPojoClass = getTargetPojoRule().getTargetPojoClass();
        List<Method> getterMethods = Stream.of(targetPojoClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("get") && m.isAccessible()).collect(Collectors.toList());
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
    public void shouldTestAllsetterMethods() throws InstantiationException, IllegalAccessException {
        Class<?> targetPojoClass = getTargetPojoRule().getTargetPojoClass();
        List<Method> getterMethods = Stream.of(targetPojoClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("set") && m.isAccessible()).collect(Collectors.toList());
        if (null != getterMethods) {
            getterMethods.parallelStream().forEach(method -> {
                try {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (null != paramTypes && paramTypes.length == 1) {
                        Object[] params = new Object[paramTypes.length];
                        if (Set.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Collections.EMPTY_SET;
                        } else if (List.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Collections.EMPTY_LIST;
                        } else if (Boolean.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Boolean.TRUE;
                        } else if (Integer.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Integer.valueOf(0);
                        } else if (Float.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Float.valueOf(0);
                        } else if (Long.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = 0L;
                        } else if (Double.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = 0.0;
                        } else if (Short.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = Short.valueOf((short) 0);
                        } else if (Number.class.isAssignableFrom(paramTypes[0])) {
                            params[0] = 0;
                        } else if (paramTypes[0].isPrimitive()) {
                            if(Boolean.TYPE.equals(paramTypes[0])) {
                                params[0] = true;
                            }  else if(Byte.TYPE.equals(paramTypes[0])) {
                                params[0] = Byte.MIN_VALUE;
                            } else if(Character.TYPE.equals(paramTypes[0])) {
                                params[0] = 'c';
                            } else if(Short.TYPE.equals(paramTypes[0])) {
                                params[0] = 1;
                            } else if(Integer.TYPE.equals(paramTypes[0])) {
                                params[0] = 1;
                            }  else if(Long.TYPE.equals(paramTypes[0])) {
                                params[0] = 1L;
                            } else if(Float.TYPE.equals(paramTypes[0])) {
                                params[0] = 1.0F;
                            } else if(Double.TYPE.equals(paramTypes[0])) {
                                params[0] = 1.0;
                            } 
                            
                        } else {
                            params[0] = paramTypes[0].newInstance();
                        }
                        method.invoke(targetPojoObject, params);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    @Test
    public void shouldTestHashCode() throws InstantiationException, IllegalAccessException {
        targetPojoObject.hashCode();
    }

    @Test
    public void shouldRetutnTrueFromEqualsIfThisRefEqualsOther() throws InstantiationException, IllegalAccessException {
        Object left = targetPojoObject;
        Object other = left;
        assertEquals(true, left.equals(other));
    }

    @Test
    public void shouldRetutnFalseFromEqualsIfOtherIsNull() throws InstantiationException, IllegalAccessException {
        Object left = targetPojoObject;
        Object other = null;
        assertEquals(false, left.equals(other));
    }

    @Test
    public void shouldRetutnFalseFromEqualsIfOtherIsNotInstanceofThis()
            throws InstantiationException, IllegalAccessException {
        Object left = targetPojoObject;
        Object other = new Object();
        assertEquals(false, left.equals(other));
    }

    @Test
    public void shouldGenerateToString() throws InstantiationException, IllegalAccessException {
        targetPojoObject.toString();
    }
}
