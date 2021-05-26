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

    protected List<TypeRef> typesToTypeRefs(List<Type> types) {
        List<TypeRef> argumentTypeRefs = new ArrayList<>();
        for (int i=0; i < types.size(); i++) {
            Type argType = types.get(i);
            if ((i > 0) && argType.isAbstract()) {
                boolean found = false;
                for (int j=0; j < i; j++) {
                    if (types.get(j) == argType) {
                        argumentTypeRefs.add(argumentTypeRefs.get(j));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    argumentTypeRefs.add(new TypeRef());
                }
            } else {
                argumentTypeRefs.add(new TypeRef(argType));
            }
        }
        return argumentTypeRefs;
    }

    @Override
    public void unify(TypeRef unifyWith, Environment<TypeRef> env) throws LispException {
        TypeRef getFunctionType = new TypeRef();
        try {
            targetExpression.unify(getFunctionType, env);
        } catch (UnifyException exc) {
            throw UnifyException.addCause("Unable to unify function in function application", exc);
        }

        if (!(getFunctionType.type instanceof FunctionType)) {
            throw new UnifyException("Unable to unify function type with "+getFunctionType.type+" in apply");
        }

        FunctionType functionType = (FunctionType) getFunctionType.type;

        if (parameters.size() > functionType.arity) {
            throw new UnifyException("Unable to apply "+parameters.size()+" arguments to a "+
                    functionType.arity+" argument function");
        }

        List<TypeRef> argumentTypeRefs = typesToTypeRefs(functionType.argumentTypes);

        if (parameters.size() < functionType.arity) {
            for (int i=0; i < parameters.size(); i++) {
                try {
                    parameters.get(i).unify(argumentTypeRefs.get(i), env);
                } catch (UnifyException exc) {
                    throw UnifyException.addCause("Unable to unify function argument", exc);
                }
            }
            List<Type> partialParams = new ArrayList<>();
            for (int i=parameters.size(); i < functionType.arity; i++) {
                partialParams.add(argumentTypeRefs.get(i).type);
            }
            FunctionType partialType = new FunctionType(functionType.arity - parameters.size(),
                    partialParams, functionType.returnType);

            if (unifyWith.type.isAbstract()) {
                unifyWith.type = partialType;
            } else if (!(unifyWith.type instanceof FunctionType)) {
                throw new UnifyException("Unable to unify partial function application with "+unifyWith.type);
            } else {
                FunctionType otherFunc = (FunctionType) unifyWith.type;
                List<TypeRef> prefs = typesToTypeRefs(partialParams);
                List<TypeRef> orefs = typesToTypeRefs(otherFunc.argumentTypes);

                for (int i=0; i < partialType.arity; i++) {
                    TypeRef oref = orefs.get(i);
                    if (oref.type.isAbstract()) {

                    }
                }
            }

        } else {

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
