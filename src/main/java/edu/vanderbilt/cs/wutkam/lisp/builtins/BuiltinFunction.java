package edu.vanderbilt.cs.wutkam.lisp.builtins;

import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;
import edu.vanderbilt.cs.wutkam.lisp.type.Type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 9:19 AM
 */
public abstract class BuiltinFunction implements Expression {
    protected final Type type;

    public BuiltinFunction(Type type) {
        this.type = type;
    }
}
