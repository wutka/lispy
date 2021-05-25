package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.SymbolType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.Objects;

public class SymbolExpr implements Expression {
    public String value;

    public SymbolExpr(String value) {
        this.value = value;
    }

    public void unify(TypeRef unifyWith) throws LispException {
        if (unifyWith.type instanceof AbstractType) {
            unifyWith.setType(SymbolType.TYPE);
            return;
        } else if (!unifyWith.type.equals(SymbolType.TYPE)){
            throw new LispException("Unable to unify type with SymbolType");
        } else {
            return;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolExpr that = (SymbolExpr) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
