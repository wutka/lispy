package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.IntType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.Objects;

public class IntExpr implements Expression {
    public final int value;

    public IntExpr(int value) {
        this.value = value;
    }

    public void unify(TypeRef unifyWith) throws LispException {
        if (unifyWith.type instanceof AbstractType) {
            unifyWith.setType(IntType.TYPE);
            return;
        } else if (!unifyWith.type.equals(IntType.TYPE)){
            throw new LispException("Unable to unify type "+unifyWith.type.toString()+" with IntType");
        } else {
            return;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntExpr intExpr = (IntExpr) o;
        return value == intExpr.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
