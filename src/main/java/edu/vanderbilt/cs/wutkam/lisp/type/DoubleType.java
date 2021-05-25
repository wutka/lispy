package edu.vanderbilt.cs.wutkam.lisp.type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:47 PM
 */
public class DoubleType extends Type {
    public static final Type TYPE = new DoubleType();

    public DoubleType() {}

    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (otherObj instanceof DoubleType) return true;
        return false;
    }

    public int hashCode() {
        return 53;
    }
}
