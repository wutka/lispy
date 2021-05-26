package edu.vanderbilt.cs.wutkam.lisp.type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:47 PM
 */
public class ConsType extends Type {
    public final Type elementType;

    public ConsType() {
        elementType = new AbstractType();
    }

    public ConsType(Type elementType) {
        this.elementType = elementType;
    }

    public String toString() {
        return "cons "+elementType.toString();
    }

    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (!(otherObj instanceof ConsType)) return false;
        ConsType other = (ConsType) otherObj;
        return elementType.equals(other.elementType);
    }

    public int hashCode() {
        return 71;
    }
}
