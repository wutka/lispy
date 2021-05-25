package edu.vanderbilt.cs.wutkam.lisp.type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:47 PM
 */
public class ListType extends Type {
    public final Type elementType;

    public ListType() {
        elementType = new Type();
    }

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (!(otherObj instanceof ListType)) return false;
        ListType other = (ListType) otherObj;
        return elementType.equals(other.elementType);
    }

    public int hashCode() {
        return 71;
    }
}
