package edu.vanderbilt.cs.wutkam.lisp.parser;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 10:36 AM
 */
public class TestParser {
    @Test
    public void testExprs() throws LispException {
        List<Expression> result = Parser.parse("(list 1 2 5.5 foo #f bar \"baz\" \"quux\" -5 -foo- #t)");
        assertEquals(result.size(), 1, "Result should have 1 item");
        ListExpr listExpr = (ListExpr) result.get(0);
        assertEquals(listExpr.elements.size(), 12, "Result should have 12 items");
        assertEquals(listExpr.elements.get(0), new SymbolExpr("list"), "item 0 should be a SymbolExpr(list)");
        assertEquals(listExpr.elements.get(1), new IntExpr(1), "item 1 should be a IntExpr(1)");
        assertEquals(listExpr.elements.get(2), new IntExpr(2), "item 2 should be a IntExpr(2)");
        assertEquals(listExpr.elements.get(3), new DoubleExpr(5.5), "item 3 should be a DoubleExpr(5.5)");
        assertEquals(listExpr.elements.get(4), new SymbolExpr("foo"), "item 4 should be a SymbolExpr(foo)");
        assertEquals(listExpr.elements.get(5), new BoolExpr(false), "item 5 should be a BoolExpr(false)");
        assertEquals(listExpr.elements.get(6), new SymbolExpr("bar"), "item 6 should be a SymbolExpr(bar)");
        assertEquals(listExpr.elements.get(7), new StringExpr("baz"), "item 7 should be a StringExpr(baz)");
        assertEquals(listExpr.elements.get(8), new StringExpr("quux"), "item 8 should be a StringExpr(quux)");
        assertEquals(listExpr.elements.get(9), new IntExpr(-5), "item 9 should be a IntExpr(-5)");
        assertEquals(listExpr.elements.get(10), new SymbolExpr("-foo-"), "item 10 should be a SymbolExpr(-foo-)");
        assertEquals(listExpr.elements.get(11), new BoolExpr(true), "item 11 should be a BoolExpr(true)");
        assertEquals(listExpr, new ListExpr(new ArrayList<>(Arrays.asList(
                new SymbolExpr("list"),
                new IntExpr(1),
                new IntExpr(2),
                new DoubleExpr(5.5),
                new SymbolExpr("foo"),
                new BoolExpr(false),
                new SymbolExpr("bar"),
                new StringExpr("baz"),
                new StringExpr("quux"),
                new IntExpr(-5),
                new SymbolExpr("-foo-"),
                new BoolExpr(true)
        ))), "List equality fails");

        result = Parser.parse("foo \"bar\" -baz- quux");
        assertEquals(result.size(), 4, "Result should have 4 items");

        result = Parser.parse("");
        assertEquals(result.size(), 0, "Expected 0-length result for empty string");

        result = Parser.parse("()");
        assertEquals(result.size(), 1, "Expected single item in result for empty expr");
        listExpr = (ListExpr) result.get(0);
        assertEquals(listExpr.elements.size(), 0, "Expected 0-length list for ()");

        assertThrows(LispException.class, () -> {
            Parser.parse("(((foo))");
        });

    }
}
