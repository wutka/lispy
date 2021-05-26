package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.runtime.Environment;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.ConsType;
import edu.vanderbilt.cs.wutkam.lisp.type.Type;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 9:03 AM
 */
public class ConsExpr implements Expression {
    public final Expression head;
    public final ConsExpr tail;

    public ConsExpr() {
        head = null;
        tail = null;
    }

    public ConsExpr(Expression head, ConsExpr tail) {
        this.head = head;
        this.tail = tail;
    }

    public ConsExpr(List<Expression> list) {
        ConsExpr curr = null;
        for (int i=list.size()-1; i >= 1; i--) {
            curr = new ConsExpr(list.get(i), curr);
        }
        if (list.size() > 0) {
            head = list.get(0);
            tail = curr;
        } else {
            head = null;
            tail = null;
        }
    }

    @Override
    public Expression evaluate(Environment env) throws LispException {
        Expression headEval = null;
        Expression tailEval = null;

        if (head != null) {
            headEval = head.evaluate(env);
        }
        if (tail != null) {
            tailEval = tail.evaluate(env);
        }
        return new ConsExpr(headEval, (ConsExpr) tailEval);
    }

    @Override
    public void unify(TypeRef unifyWith, Environment env) throws LispException {
        ConsType thisType;
        if (head == null) {
            thisType = new ConsType();
        } else {
            TypeRef headType = new TypeRef();
            head.unify(headType, env);
            if (headType.type.isAbstract()) {
                thisType = new ConsType();
            } else {
                thisType = new ConsType(headType.type);
            }
        }

        if (unifyWith.type.isAbstract()) {
            unifyWith.type = thisType;
        } else if (unifyWith.type instanceof ConsType) {
            ConsType other = (ConsType) unifyWith.type;
            if (other.elementType.isAbstract() && !thisType.elementType.isAbstract()) {
                unifyWith.type = thisType;
            }
        } else {
            throw new UnifyException("Unable to unify "+thisType+" with "+unifyWith.type);
        }
    }
}
