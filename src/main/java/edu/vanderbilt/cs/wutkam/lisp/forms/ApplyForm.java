package edu.vanderbilt.cs.wutkam.lisp.forms;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;
import edu.vanderbilt.cs.wutkam.lisp.expr.ListExpr;
import edu.vanderbilt.cs.wutkam.lisp.expr.SymbolExpr;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 4:41 PM
 */
public class ApplyForm implements Form {
    @Override
    public Expression expandForm(ListExpr aList) throws LispException {
        Expression firstElement = aList.elements.get(0);

        if (firstElement instanceof SymbolExpr) {
            Expression

        } else if (firstElement instanceof ListExpr) {

        }
        return null;
    }
}
