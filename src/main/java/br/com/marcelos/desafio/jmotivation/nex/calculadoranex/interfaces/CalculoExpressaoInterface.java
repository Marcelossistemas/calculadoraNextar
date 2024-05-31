package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.interfaces;

import java.util.List;


/**
 * @Author - marcelos
 * @version 1.0
 * Interface definida para permitir a implementação do Calculo sem utilizar Biblioteca do Próprio Java
 */
public interface CalculoExpressaoInterface {

	public List<String> calculaSinalMD(int posicao, List<String> ll);
	public List<String> calculaSinalSS(int posicao, List<String> ll);
	public String resultado (String expressao);
	public int verificaSinais(List<String> expressao);
	public String formatarExpressao(String expressao);
	
}
