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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PojoWithNoDefaultConstructor)) {
            return false;
        }
        PojoWithNoDefaultConstructor other = (PojoWithNoDefaultConstructor) obj;
        if (value != other.value) {
            return false;
        }
        return true;
    }



}
