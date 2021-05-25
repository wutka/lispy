package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.type.BoolType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 4:35 PM
 */
public class IfExpr implements Expression {
    public final Expression test;
    public final Expression trueOption;
    public final Expression falseOption;

    public IfExpr(Expression test, Expression trueOption, Expression falseOption)
    {
        this.test = test;
        this.trueOption = trueOption;
        this.falseOption = falseOption;
    }

    @Override
    public Expression evaluate(Environment env) throws LispException {
        Expression testResult = test.evaluate(env);
        if (testResult instanceof BoolExpr) {
            if (((BoolExpr)testResult).value) {
                return trueOption.evaluate(env);
            } else {
                return falseOption.evaluate(env);
            }
        } else {
            throw new LispException("If expression test did not evaluate to a boolean");
        }
    }

    @Override
    public TypeRef unify(TypeRef unifyWith) throws LispException {
        TypeRef boolRef = new TypeRef(BoolType.TYPE);
        try {
            test.unify(boolRef);
        } catch (LispException exc) {
    }

    @Override
    public TypeRef unify(TypeRef unifyWith) throws LispException {
        return null;
    }
}
