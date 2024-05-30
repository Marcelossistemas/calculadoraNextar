package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.interfaces.CalculoExpressaoInterface;
/**
 * @author marcelos
 * @version 1.0
 * Implementação do Calculo da expressão matemática.
 */
@Service
public class CalculoExpressaoService implements CalculoExpressaoInterface {

	/**
	 * Este método efetua a verificação e o calculo para as operações de multiplicação e Divisão primeiro
	 */
	@Override
	public List<String> calculaSinalMD(int posicao, List<String> ll) {
		var resultado = 0.0;
		if (ll.get(posicao).equals("*")) {
			resultado = Double.parseDouble(ll.get(posicao - 1)) * Double.parseDouble(ll.get(posicao + 1));
			System.out.println(resultado);
		} else if (ll.get(posicao).equals("/")) {
			resultado = Double.parseDouble(ll.get(posicao - 1)) / Double.parseDouble(ll.get(posicao + 1));
			System.out.println(resultado);
		}

		ll.set(posicao - 1, "" + resultado);
		if ((posicao + 2) < ll.size()) {
			ll.set(posicao, ll.get(posicao + 2));
		} else {
			ll.set(posicao, ll.get(posicao + 1));
		}
		ll.remove(posicao + 1);
		ll.remove(posicao);

		return ll;
	}

	/**
	 * Este método efetua a verificação e o calculo para as operações de Adição e Subtração
	 */
	@Override
	public List<String> calculaSinalSS(int posicao, List<String> ll) {
		var resultado = 0.0;
		if (ll.get(posicao).equals("+")) {
			resultado = Double.parseDouble(ll.get(posicao - 1)) + Double.parseDouble(ll.get(posicao + 1));
		} else if (ll.get(posicao).equals("-")) {
			resultado = Double.parseDouble(ll.get(posicao - 1)) - Double.parseDouble(ll.get(posicao + 1));
		}

		ll.set(posicao - 1, "" + resultado);
		if ((posicao + 2) < ll.size()) {
			ll.set(posicao, ll.get(posicao + 2));
		} else {
			ll.set(posicao, ll.get(posicao + 1));
		}
		ll.remove(posicao + 1);
		ll.remove(posicao);

		return ll;
	}

	/**
	 * Este método utiliza de recursividade para efetuar o calculo da expressão
	 */
	@Override
	public List<String> resultado(String expressao) {
		List<String> listaexpressao = new ArrayList<String>();
		StringTokenizer strToken = new StringTokenizer(this.formatarExpressao(expressao.trim()));

		while (strToken.hasMoreTokens()) {
			listaexpressao.add(strToken.nextToken());
		}

		int posicaoSinal = this.verificaSinais(listaexpressao);
		if (posicaoSinal != -1) {
			listaexpressao = this.calculaSinalMD(posicaoSinal, listaexpressao);
		} else {
			listaexpressao = this.calculaSinalSS(1, listaexpressao);
		}

		if (listaexpressao.size() == 1) {
			return listaexpressao;
		} else {
			String express = String.join(", ", listaexpressao);
			express = express.replaceAll(",", " ");
			System.out.println(express);

			return resultado(express);
		}
	}

	/**
	 * Este método é utilizado para verificar se dentro da Expressão há operação de multiplicação ou divisão.
	 */
	@Override
	public int verificaSinais(List<String> expressao) {
		for (String s : expressao) {
			if (s.equals("*") || s.equals("/")) {
				return expressao.indexOf(s);
			}
		}
		return -1;
	}

	@Override
	public String formatarExpressao(String expressao) {
		String formatacao = expressao.replaceAll("([\\+\\-\\*/])", " $1 ");
        return formatacao.trim();
	}

}
