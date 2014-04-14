package br.com.arquitetura.exception;

/**
 * Classe responsável por lançar exceções de validação.
 * @author Wesley Luiz
 * @version 1.0.0
 */
public class ValidacaoException extends RuntimeException {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public ValidacaoException() {
		super();
	}

	public ValidacaoException(final String msg) {
		super(msg);
	}
}
