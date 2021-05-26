package edu.vanderbilt.cs.wutkam.lisp.runtime;

import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 3:14 PM
 */
public class Environment<T> {
    protected Map<String, T> symbols;
    protected Environment<T> next;

    public Environment() {
        symbols = new HashMap<>();
        next = null;
    }

    public Environment(Environment<T> next) {
        symbols = new HashMap<>();
        this.next = next;
    }

    public T lookup(String symbol) {
        T result = symbols.get(symbol);
        if (result != null) return result;
        if (next != null) return next.lookup(symbol);
        return null;
    }

    public void define(String name, T expr) {
        symbols.put(name, expr);
    }
}
