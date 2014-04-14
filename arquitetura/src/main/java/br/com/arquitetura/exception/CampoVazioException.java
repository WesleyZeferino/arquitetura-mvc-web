package br.com.arquitetura.exception;

/**
 * Classe responsável por tratar exceções de validação de atributos das <i>classes</i> que herdam de <code>Entidade</code>.
 * @author Wesley Luiz
 * @version 1.0.0
 */
public class CampoVazioException extends ValidacaoException {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 */
	public CampoVazioException() {
		super();
	}

	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 * @param field- Recebe o campo que gerou a exceção.
	 */
	public CampoVazioException(final String field) {
		super("O campo '" + field + "' não pode ser vazio!");
	}

}
