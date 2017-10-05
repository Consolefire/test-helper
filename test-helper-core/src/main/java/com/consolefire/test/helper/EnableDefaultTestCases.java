package com.consolefire.test.helper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EnableDefaultTestCases {

    String[] packagesToScan() default {};

    Class<?>[] targetClasses() default {};

}
