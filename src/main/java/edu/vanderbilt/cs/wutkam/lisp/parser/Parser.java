package edu.vanderbilt.cs.wutkam.lisp.parser;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.expr.*;
import edu.vanderbilt.cs.wutkam.lisp.forms.FormExpander;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
    public List<Expression> items = new ArrayList<>();
    protected Stack<List<Expression>> expressionStack = new Stack<>();

    public static List<Expression> parse(String str) throws LispException {
        return parse(new StringReader(str));
    }

    public static List<Expression> parse(Reader rdr) throws LispException {
        Parser parser = new Parser();
        parser.parseImpl(rdr);
        return parser.items;
    }

    protected final String symbolChars = "*/+-!@#$%&_=:.<>";

    protected boolean isSymbolChar(int ch) {
        return Character.isAlphabetic(ch) || symbolChars.indexOf(ch) >= 0;
    }

    protected void addExpression(Expression expr) {
        if (expressionStack.isEmpty()) {
            items.add(expr);
        } else {
            expressionStack.peek().add(expr);
        }
    }
    protected List<Expression> parseImpl(Reader rdr) throws LispException {
        PushbackReader pushback = new PushbackReader(rdr);

        char ch;

        try {
            while ((ch = (char) pushback.read()) != (char) -1) {
                if (ch == '(') {
                    expressionStack.push(new ArrayList<>());
                } else if (ch == ')') {
                    List<Expression> expr = expressionStack.pop();
                    ListExpr listExpr = new ListExpr(expr);
                    Expression expanded = FormExpander.expand(listExpr);
                    if (expressionStack.isEmpty()) {
                        items.add(expanded);
                    } else {
                        expressionStack.peek().add(expanded);
                    }
                } else if (ch == '"') {
                    StringBuilder builder = new StringBuilder();
                    boolean escape = false;

                    while (((ch = (char) pushback.read()) != (char) -1)) {
                        if (escape) {
                            if (ch == 'n') {
                                builder.append('\n');
                            } else if (ch == 'r') {
                                builder.append('\r');
                            } else if (ch == 't') {
                                builder.append('\t');
                            } else {
                                builder.append(ch);
                            }
                            escape = false;
                        } else if (ch == '\\') {
                            escape = true;
                        } else if (ch == '"') {
                            break;
                        } else {
                            builder.append(ch);
                        }

                    }
                    if (ch < 0) {
                        throw new LispException("Unexpected end of stream, possible missing end-\"");
                    }
                    addExpression(new StringExpr(builder.toString()));
                } else if (isSymbolChar(ch)) {
                    if (ch == '-') {
                        int ch2 = pushback.read();
                        if (Character.isDigit(ch2)) {
                            int num = ch2-'0';
                            boolean inDouble = false;
                            double doubleFrac = 0.1;
                            double doubleNum = 0.0;
                            while (((ch = (char) pushback.read()) != (char) -1) && (Character.isDigit(ch) || ch == '.')) {
                                if (inDouble) {
                                    if (ch == '.') {
                                        throw new LispException("Extra . in double value");
                                    }
                                    doubleNum = doubleNum + ((double) (ch-'0')) * doubleFrac;
                                    doubleFrac = doubleFrac / 10.0;
                                } else if (ch == '.') {
                                    doubleNum = num;
                                    inDouble = true;
                                } else {
                                    num = num * 10 + ch - '0';
                                }
                            }
                            pushback.unread(ch);
                            Expression numValue;
                            if (inDouble) {
                                numValue = new DoubleExpr(-doubleNum);
                            } else {
                                numValue = new IntExpr(-num);
                            }
                            addExpression(numValue);
                            continue;
                        }  else {
                            pushback.unread(ch2);
                        }
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append(ch);
                    while (((ch = (char) pushback.read()) != (char) -1) && (isSymbolChar(ch) || Character.isDigit(ch))) {
                        builder.append(ch);
                    }
                    pushback.unread(ch);
                    String symbol = builder.toString();
                    if (symbol.equals("#t")) {
                        addExpression(new BoolExpr(true));
                    } else if (symbol.equals("#f")) {
                        addExpression(new BoolExpr(false));
                    } else {
                        addExpression(new SymbolExpr(symbol));
                    }
                } else if (Character.isDigit(ch)) {
                    int num = ch - '0';
                    boolean inDouble = false;
                    double doubleNum = 0.0;
                    double doubleFrac = 0.1;
                    while (((ch = (char) pushback.read()) != (char) -1) && (Character.isDigit(ch) || ch == '.')) {
                        if (inDouble) {
                            if (ch == '.') {
                                throw new LispException("Extra . in double value");
                            }
                            doubleNum = doubleNum + ((double) (ch-'0')) * doubleFrac;
                            doubleFrac = doubleFrac / 10.0;
                        } else if (ch == '.') {
                            doubleNum = num;
                            inDouble = true;
                        } else {
                            num = num * 10 + ch - '0';
                        }
                    }
                    pushback.unread(ch);
                    Expression numValue;
                    if (inDouble) {
                        numValue = new DoubleExpr(doubleNum);
                    } else {
                        numValue = new IntExpr(num);
                    }
                    addExpression(numValue);
                } else if (Character.isWhitespace(ch)) {
                } else {
                    throw new LispException("Unexpected character - "+ ch);
                }
            }
        } catch (IOException exc) {
            throw new LispException("I/O Error reading stream: "+exc.getMessage());
        }
        if (!expressionStack.isEmpty()) {
            throw new LispException("Incomplete expression, too few )'s?");
        }
        return items;
    }
}
