package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model;


import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.util.ExpressionEvaluator;

import javax.persistence.*;
import javax.script.ScriptException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Entity
public class Expressao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expressao;

    private String resultado;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Transient
    public void calcularResultado() throws ScriptException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        Object evalResult = evaluator.evaluate(this.expressao);
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.00", symbols);
        this.resultado = decimalFormat.format(Double.parseDouble(evalResult.toString()));
    }
}
