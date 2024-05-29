package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.util;


import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class ExpressionEvaluator {

    private Context context;

    public ExpressionEvaluator() {
        this.context = Context.create("js");
    }

    public Object evaluate(String expression) {
        Value result = context.eval("js", expression);
        return result.as(Object.class);
    }
}
