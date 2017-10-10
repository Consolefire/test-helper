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
public class SimplePojo {

    private Long id;
    private String name;

    /**
     * 
     */
    public SimplePojo() {
        this(1L, "Test");
    }

    public SimplePojo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof SimplePojo)) {
            return false;
        }
        SimplePojo other = (SimplePojo) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }



}
