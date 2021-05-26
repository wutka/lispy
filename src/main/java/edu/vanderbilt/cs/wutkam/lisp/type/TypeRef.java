package edu.vanderbilt.cs.wutkam.lisp.type;

import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 4:43 PM
 */
public class TypeRef {
    public Type type;

    public TypeRef() { this.type = new AbstractType(); }
    public TypeRef(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
