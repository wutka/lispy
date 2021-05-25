package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.DoubleType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.Objects;

public class DoubleExpr implements Expression {
   public final double value;

   public DoubleExpr(double value) {
       this.value = value;
   }

    public void unify(TypeRef unifyWith) throws LispException {
        if (unifyWith.type instanceof AbstractType) {
            unifyWith.setType(DoubleType.TYPE);
            return;
        } else if (!unifyWith.type.equals(DoubleType.TYPE)){
            throw new LispException("Unable to unify type with DoubleType");
        } else {
            return;
        }
    }

    @Override
   public String toString() {
       return Double.toString(value);
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleExpr that = (DoubleExpr) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
