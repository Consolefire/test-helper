/**
 * 
 */


package com.consolefire.test.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

/**
 * @author sabuj.das
 *
 */
public abstract class MultiplePojoTestRunner {

    private Map<Class<?>, ReflectionBasedPojoTestRule> pojoTestRules = new HashMap<>();
    private Map<Class<?>, TargetObjectProvider> customTargetObjectProviders = new HashMap<>();

    public abstract Map<Class<?>, ReflectionBasedPojoTestRule> buildPojoTestRules();

    public abstract Map<Class<?>, TargetObjectProvider> buildCustomTargetObjectProviders();

    @Before
    public void init() {
        this.pojoTestRules = buildPojoTestRules();
        this.customTargetObjectProviders = buildCustomTargetObjectProviders();
    }

    @Test
    public void executeAllTests() {
        List<DefaultPojoTestRunner> pojoTestRunners = new ArrayList<>();
        if (null != pojoTestRules && !pojoTestRules.isEmpty()) {
            pojoTestRules.keySet().forEach(clazz -> {
                DefaultPojoTestRunner defaultPojoTestRunner = new DefaultPojoTestRunner() {

                    @Override
                    public ReflectionBasedPojoTestRule getTargetPojoRule() {
                        return MultiplePojoTestRunner.this.getTargetPojoRule(clazz);
                    }

                    @Override
                    public <T> Object getTargetObject(Class<T> clazz) {
                        TargetObjectProvider objectProvider =
                                MultiplePojoTestRunner.this.getTargetObjectProvider(clazz);
                        if (null != objectProvider) {
                            return objectProvider.getTargetObject(clazz);
                        }
                        return super.getTargetObject(clazz);
                    }

                };
                defaultPojoTestRunner.setup();
                pojoTestRunners.add(defaultPojoTestRunner);
            });
        }

        pojoTestRunners.forEach(runner -> {
            Method[] methods = runner.getClass().getMethods();
            if (null != methods && methods.length > 0) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    if (method.isAnnotationPresent(Test.class)) {
                        try {
                            method.invoke(runner, new Object[] {});
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public ReflectionBasedPojoTestRule getTargetPojoRule(Class<?> clazz) {
        ReflectionBasedPojoTestRule rule = pojoTestRules.get(clazz);
        if (null == rule) {
            return new ReflectionBasedPojoTestRule(clazz);
        }
        return rule;
    }

    public TargetObjectProvider getTargetObjectProvider(Class<?> clazz) {
        if (null != customTargetObjectProviders && !customTargetObjectProviders.isEmpty()
                && customTargetObjectProviders.containsKey(clazz)) {
            return customTargetObjectProviders.get(clazz);
        }
        return null;
    }

}
