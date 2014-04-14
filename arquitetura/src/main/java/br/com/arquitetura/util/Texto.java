package br.com.arquitetura.util;

import br.com.arquitetura.objeto.Objeto;

/**
 * A classe <code>Texto</code> é responsável por fornecer métodos de manipulação e transformação de <code>String</code>.
 * 
 * @author Wesley Luiz
 * @version 1.0.0
 */
public final class Texto extends Objeto {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Atributo SPACE. */
	private static final String SPACE = " ";

	/**
	 * Método responsável por transformar a primeira letra de uma <code>String</code> em maiúscula.
	 * @author Wesley Luiz
	 * @param valor - <code>String</code> que vai ser transformada.
	 * @return Retorna o valor transformado.
	 */
	public static String toPrimeiraLetraMaiuscula(final String valor) {
		return valor.substring(0, 1).toUpperCase().concat(valor.substring(1));
	}

	/**
	 * Método responsável por transformar as primeiras letras de todas palavras de uma <code>String</code> em maiúscula.
	 * @author Wesley Luiz
	 * @param valor - <code>String</code> que vai ser transformada.
	 * @return Retorna o valor transformado.
	 */
	public static String toPrimeirasLetrasMaiusculas(final String valor) {
		StringBuilder sb = new StringBuilder();
		for (String str : valor.split(SPACE)) {
			sb.append(toPrimeiraLetraMaiuscula(str)).append(SPACE);
		}
		return sb.toString().trim();
	}
}
