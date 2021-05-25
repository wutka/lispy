package edu.vanderbilt.cs.wutkam.lisp.type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:47 PM
 */
public class IntType extends Type {
    public static final Type TYPE = new IntType();

    public IntType() {}

    public String toString() { return "int"; }

    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (otherObj instanceof IntType) return true;
        return false;
    }

    public int hashCode() {
        return 41;
    }
}
