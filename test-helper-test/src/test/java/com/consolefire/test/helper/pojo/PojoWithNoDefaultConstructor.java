/**
 * 
 */


package com.consolefire.test.helper.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sabuj.das
 *
 */
@Getter
@Setter
public class PojoWithNoDefaultConstructor {

    private int value;
    private String txt;
    private double d;
    private float f;
    private short s;
    private char c;

    public PojoWithNoDefaultConstructor(int value) {
        this.value = value;
    }



}
