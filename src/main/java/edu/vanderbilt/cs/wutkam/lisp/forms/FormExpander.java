package edu.vanderbilt.cs.wutkam.lisp.forms;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;
import edu.vanderbilt.cs.wutkam.lisp.expr.ListExpr;
import edu.vanderbilt.cs.wutkam.lisp.expr.SymbolExpr;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 4:21 PM
 */
public class FormExpander {
    public static Map<String,Form> specialForms = new HashMap<>();

    public static Expression expand(ListExpr aList) throws LispException
    {
        if (aList.elements.size() < 1) {
            return aList;
        }

        Expression firstElem = aList.elements.get(0);
        if (firstElem instanceof SymbolExpr) {
            SymbolExpr sym = (SymbolExpr) firstElem;
            Form expander = specialForms.get(sym);
            if (expander == null) {
                return expander.expandForm(aList);
            }
        }
        return aList;
    }
}
