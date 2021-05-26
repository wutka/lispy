package edu.vanderbilt.cs.wutkam.lisp.expr;

import edu.vanderbilt.cs.wutkam.lisp.LispException;
import edu.vanderbilt.cs.wutkam.lisp.runtime.Environment;
import edu.vanderbilt.cs.wutkam.lisp.type.AbstractType;
import edu.vanderbilt.cs.wutkam.lisp.type.FunctionType;
import edu.vanderbilt.cs.wutkam.lisp.type.TypeRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/25/21
 * Time: 2:08 PM
 */
public class ApplyExpr implements Expression {
    public final Expression targetExpression;
    public final List<Expression> parameters;

    public ApplyExpr(Expression targetExpression, List<Expression> parameters) {
        this.targetExpression = targetExpression;
        this.parameters = parameters;
    }

    @Override
    public Expression evaluate(Environment<Expression> env) throws LispException {
        FunctionExpr targetFunction = (FunctionExpr) targetExpression.evaluate(env);

        Environment<Expression> funcEnv = new Environment<>(env);

        for (int i=0; i < targetFunction.parameterList.size(); i++) {
            SymbolExpr sym = targetFunction.parameterList.get(i);
            if (i < targetFunction.partialArgs.size()) {
                funcEnv.define(sym.value, targetFunction.partialArgs.get(i).evaluate(env));
            } else {
                int argNum = i - targetFunction.partialArgs.size();
                funcEnv.define(sym.value, parameters.get(argNum).evaluate(env));
            }
        }
        return targetFunction.targetExpression.evaluate(funcEnv);
    }

    @Override
    public void unify(TypeRef unifyWith, Environment<TypeRef> env) throws LispException {
        TypeRef functionType = new TypeRef();
        try {
            targetExpression.unify(functionType, env);
        } catch (UnifyException exc) {
            throw UnifyException.addCause("Unable to unify function in function application", exc);
        }

        if (!(functionType.type instanceof FunctionType)) {
            throw new UnifyException("Unable to unify function type with "+functionType.type+" in apply");
        }
        
        if (targetExpression instanceof FunctionExpr) {
            FunctionExpr targetFunction = (FunctionExpr) targetExpression;
            if (parameters.size() + targetFunction.partialArgs.size() > targetFunction.arity) {
                throw new UnifyException("Unable to unify function application with too many parameters");
            } else if (parameters.size() + targetFunction.partialArgs.size() == targetFunction.arity) {
                unifyFunctionCall(unifyWith, targetFunction, env);
            } else {
                FunctionExpr partialFunc = new FunctionExpr(targetFunction, parameters);
                partialFunc.unify(unifyWith, env);
            }
        }
    }

    protected void unifyFunctionCall(TypeRef unifyWith, FunctionExpr targetFunction,
                                     Environment<TypeRef> env) throws LispException {
        Environment<TypeRef> funcEnv = new Environment<>(env);

        char tagChar = 'a';
        for (int i=0; i < targetFunction.parameterList.size(); i++) {
            SymbolExpr sym = targetFunction.parameterList.get(i);
            if (i < targetFunction.partialArgs.size()) {
                TypeRef paramRef = new TypeRef();
                Expression partialArg = targetFunction.partialArgs.get(i);
                try {
                    partialArg.unify(paramRef, env);
                } catch (UnifyException exc) {
                    UnifyException.addCause("Unable to unify partial argument "+i, exc);
                }
                funcEnv.define(sym.value, new TypeRef(new AbstractType("'" + tagChar++)));
            } else {
                TypeRef paramRef = new TypeRef();
                int argNum = i - targetFunction.partialArgs.size();
                try {
                    parameters.get(argNum).unify(paramRef, env);
                } catch (UnifyException exc) {
                    UnifyException.addCause("Unable to unify argument " + argNum, exc);
                }
                funcEnv.define(sym.value, new TypeRef(new AbstractType("'" + tagChar++)));
            }
        }
        TypeRef returnType = new TypeRef();
        try {
            targetFunction.targetExpression.unify(returnType, funcEnv);
            if (unifyWith.type.isAbstract()) {
                unifyWith.type = returnType.type;
            } else if (!unifyWith.type.equals(returnType.type)) {
                throw new UnifyException("Unable to unify return type");
            }
        } catch (UnifyException exc) {
            throw UnifyException.addCause("Unable to unify function expression with parameters", exc);
        }
    }
}
