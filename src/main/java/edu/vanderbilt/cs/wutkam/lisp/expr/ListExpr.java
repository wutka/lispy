package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListExpr implements Expression {
    public List<Expression> elements;

    public ListExpr() {
        elements = new ArrayList<>();
    }

    public ListExpr(List<Expression> elements) {
        this.elements = elements;
    }

    public void unifyWith(TypeRef ref) throws LispException {
        if (ref.type instanceof AbstractType) {
            
        }
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        boolean first = true;
        for (Expression elem: elements) {
            if (!first) builder.append(' ');
            builder.append(elem.toString());
        }
        builder.append(')');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListExpr listExpr = (ListExpr) o;
        return Objects.equals(elements, listExpr.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}
