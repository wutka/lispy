package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.StringType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.Objects;

public class StringExpr implements Expression {
    public String value;

    public StringExpr(String value) {
        this.value = value;
    }

    public void unify(TypeRef unifyWith) throws LispException {
        if (unifyWith.type instanceof AbstractType) {
            unifyWith.setType(StringType.TYPE);
            return;
        } else if (!unifyWith.type.equals(StringType.TYPE)){
            throw new LispException("Unable to unify type with StringType");
        } else {
            return;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('"');
        for (int i=0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '"') {
                builder.append("\\\"");
            } else if (ch == '\n') {
                builder.append("\\n");
            } else if (ch == '\r') {
                builder.append("\\r");
            } else if (ch == '\t') {
                builder.append("\\t");
            } else {
                builder.append(ch);
            }
        }
        builder.append('"');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringExpr that = (StringExpr) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
