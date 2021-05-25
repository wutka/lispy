package edu.vanderbilt.cs.wutkam.lisp.expr;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:14 PM
 */
public class Environment {
    protected Map<String,Expression> symbols;
    protected Environment next;

    public Environment() {
        symbols = new HashMap<>();
        next = null;
    }

    public Environment(Environment next) {
        symbols = new HashMap<>();
        this.next = next;
    }

    public Expression lookup(String symbol) {
        Expression result = symbols.get(symbol);
        if (result != null) return result;
        if (next != null) return next.lookup(symbol);
        return null;
    }
}
