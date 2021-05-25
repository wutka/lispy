package edu.vanderbilt.cs.wutkam.lisp.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:47 PM
 */
public class FunctionType extends Type {
    public final List<Type> elementTypes;
    public final int arity;

    public FunctionType(int arity) {
        this.arity = arity;
        List<Type> et = new ArrayList<>();
        for (int i=0; i < arity; i++) et.add(new AbstractType());
        this.elementTypes = Collections.unmodifiableList(et);
    }

    public FunctionType(int arity, List<Type> elementTypes) {
        this.arity = arity;
        this.elementTypes = Collections.unmodifiableList(elementTypes);
    }

    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (!(otherObj instanceof FunctionType)) return false;
        FunctionType other = (FunctionType) otherObj;
        return (arity == other.arity) && elementTypes.equals(other.elementTypes);
    }

    public int hashCode() {
        return 73 * arity + elementTypes.hashCode();
    }
}
