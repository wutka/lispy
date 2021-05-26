package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.runtime.Environment;
import edu.vanderbilt.cs.wutkam.lisp.type.Type;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 12:58 PM
 */

public interface Expression {
    default Expression evaluate(Environment<Expression> env) throws LispException {
        return this;
    }
    void unify(TypeRef unifyWith, Environment<TypeRef> env) throws LispException;
}
