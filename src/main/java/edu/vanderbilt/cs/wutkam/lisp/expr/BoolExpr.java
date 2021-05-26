package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.runtime.Environment;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.Type;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:18 PM
 */
public class BoolExpr implements Expression {
    public final boolean value;

    public BoolExpr(boolean value) {
        this.value = value;
    }

    public void unify(TypeRef unifyWith, Environment env) throws LispException {
        if (unifyWith.type instanceof AbstractType) {
            unifyWith.setType(BoolType.TYPE);
            return;
        } else if (!unifyWith.type.equals(BoolType.TYPE)){
            throw new UnifyException("Unable to unify type "+unifyWith.type.toString()+" with BoolType");
        } else {
            return;
        }
    }

    @Override
    public String toString()
    {
        return value ? "#t" : "#f";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolExpr boolExpr = (BoolExpr) o;
        return value == boolExpr.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
