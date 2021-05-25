package edu.vanderbilt.cs.wutkam.lisp.repl;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.Expression;
import edu.vanderbilt.cs.wutkam.lisp.parser.Parser;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.io.DataInputStream;
import java.util.List;

public class Repl {
    public static void main(String[] args) {
        DataInputStream dataIn = new DataInputStream(System.in);
        for (;;) {
            try {
                String line = dataIn.readLine();
                boolean unifyExprs = false;
                if (line.startsWith(":u ")) {
                    unifyExprs = true;
                    line = line.substring(3);
                }
                List<Expression> exprs = Parser.parse(line);
                for (Expression expr: exprs) {
                    if (unifyExprs) {
                        TypeRef ref = new TypeRef(new AbstractType());
                        expr.unify(ref);
                        System.out.println(expr+" : "+ref.type);
                    } else {
                        System.out.println(expr);
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
    }
}
