package edu.vanderbilt.cs.wutkam.lisp.runtime;

import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 9:13 AM
 */
public class Runtime {
    protected static final Environment topLevelEnvironment = new Environment();

    public static Environment getTopLevel() { return topLevelEnvironment; }

}
