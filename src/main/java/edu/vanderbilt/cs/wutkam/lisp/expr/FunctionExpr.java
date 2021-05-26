package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.runtime.Environment;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.FunctionType;
import edu.vanderbilt.cs.wutkam.lisp.type.Type;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 2:07 PM
 */
public class FunctionExpr implements Expression {
    public final int arity;
    public final List<Expression> partialArgs;
    public final Expression targetExpression;
    public final List<SymbolExpr> parameterList;

    public FunctionExpr(int arity, List<SymbolExpr> parameterList, Expression targetExpression) {
        this.arity = arity;
        this.parameterList = parameterList;
        this.targetExpression = targetExpression;
        this.partialArgs = new ArrayList<>();
    }

    public FunctionExpr(FunctionExpr partialFunc, List<Expression> partialArgs) {
        this.arity = partialFunc.arity;
        this.parameterList = partialFunc.parameterList;
        this.targetExpression = partialFunc.targetExpression;
        this.partialArgs = new ArrayList<>();
        this.partialArgs.addAll(partialFunc.partialArgs);
        this.partialArgs.addAll(partialArgs);
    }

    @Override
    public Expression evaluate(Environment<Expression> env) throws LispException {
        return Expression.super.evaluate(env);
    }

    @Override
    public void unify(TypeRef unifyWith, Environment<TypeRef> env) throws LispException {
        Environment<TypeRef> funcEnv = new Environment<>(env);

        char tagChar = 'a';
        for (int i=0; i < parameterList.size(); i++) {
            SymbolExpr sym = parameterList.get(i);
            if (i < partialArgs.size()) {
                TypeRef paramRef = new TypeRef();
                partialArgs.get(i).unify(paramRef, env);
                funcEnv.define(sym.value, new TypeRef(new AbstractType("'" + tagChar++)));
            } else {
                funcEnv.define(sym.value, new TypeRef(new AbstractType("'" + tagChar++)));
            }
        }
        TypeRef returnType = new TypeRef();
        try {
            targetExpression.unify(returnType, funcEnv);
        } catch (UnifyException exc) {
            throw UnifyException.addCause("Unable to unify function expression with parameters", exc);
        }

        List<Type> argumentTypes = new ArrayList<>();

        for (int i = partialArgs.size(); i < parameterList.size(); i++) {
            argumentTypes.add(funcEnv.lookup(parameterList.get(i).value).type);
        }

        FunctionType thisType = new FunctionType(arity - partialArgs.size(), argumentTypes, returnType.type);

        if (unifyWith.type instanceof AbstractType) {
            unifyWith.type = thisType;
        } else if (unifyWith.type instanceof FunctionType) {
            FunctionType other = (FunctionType) unifyWith.type;
            if (other.arity != thisType.arity) {
                throw new UnifyException("Function parameter count mismatch");
            }
            for (int i = 0; i < arity; i++) {
                if (!thisType.argumentTypes.get(i).equals(other.argumentTypes.get(i))) {
                    throw new UnifyException("Function parameter type mismatch, expected " +
                            thisType.argumentTypes.get(i) + " but got " +
                            other.argumentTypes.get(i));

                }
            }
            if (!(thisType.returnType.equals(other.returnType))) {
                throw new UnifyException("Function return type mismatch, expected " +
                        thisType.returnType + " but got " + other.returnType);
            }
        } else {
            throw new UnifyException("Unably to unify function " + thisType.toString() + " with " +
                    unifyWith.type.toString());
        }
    }
}
