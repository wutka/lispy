package edu.vanderbilt.cs.wutkam.lisp.forms;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.BoolExpr;
import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;
import edu.vanderbilt.cs.wutkam.lisp.expr.IfExpr;
import edu.vanderbilt.cs.wutkam.lisp.expr.ListExpr;
import edu.vanderbilt.cs.wutkam.lisp.type.ListType;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 4:34 PM
 */
public class IfForm implements Form {
    @Override
    public Expression expandForm(ListExpr aList) throws LispException {
        if (aList.elements.size() != 4) {
            throw new LispException("if expression must have both true and false paths");
        }

        Expression testExpr = aList.elements.get(1);
        if (testExpr instanceof ListType) {
            testExpr = FormExpander.expand((ListExpr) testExpr);
        }

        Expression trueExpr = aList.elements.get(2);
        if (trueExpr instanceof ListType) {
            trueExpr = FormExpander.expand((ListExpr) trueExpr);
        }

        Expression falseExpr = aList.elements.get(3);
        if (falseExpr instanceof ListType) {
            falseExpr = FormExpander.expand((ListExpr) trueExpr);
        }

        return new IfExpr(testExpr, trueExpr, falseExpr);
    }
}
