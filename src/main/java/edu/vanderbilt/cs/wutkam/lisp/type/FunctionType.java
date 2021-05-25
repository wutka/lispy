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
    public final List<Type> argumentTypes;
    public final Type returnType;
    public final int arity;

    public FunctionType(int arity) {
        this.arity = arity;
        this.returnType = new AbstractType();
        List<Type> et = new ArrayList<>();
        for (int i=0; i < arity; i++) et.add(new AbstractType());
        this.argumentTypes = Collections.unmodifiableList(et);
    }

    public FunctionType(int arity, List<Type> argumentTypes, Type returnType) {
        this.arity = arity;
        this.returnType = returnType;
        this.argumentTypes = Collections.unmodifiableList(argumentTypes);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (arity == 0) {
            builder.append("()");
        } else {
            boolean first = true;
            for (Type argType: argumentTypes) {
                if (!first) builder.append(" -> ");
                first = false;
                builder.append(argType.toString());
            }
        }
        builder.append(" -> ");
        builder.append(returnType.toString());
        return builder.toString();
    }
    public boolean equals(Object otherObj) {
        if (otherObj == null) return false;
        if (!(otherObj instanceof FunctionType)) return false;
        FunctionType other = (FunctionType) otherObj;
        return (arity == other.arity) && argumentTypes.equals(other.argumentTypes);
    }

    public int hashCode() {
        return 73 * arity + argumentTypes.hashCode();
    }
}
