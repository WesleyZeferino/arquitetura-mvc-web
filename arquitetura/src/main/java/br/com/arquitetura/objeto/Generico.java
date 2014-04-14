package br.com.arquitetura.objeto;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import br.com.arquitetura.entidade.Entidade;

/**
 * Classe responsável por agrupar funções comuns de classes genéricas, fornecendo métodos que trabalhem com <i>reflexão</i>.
 *
 * @author Wesley Luiz
 * @param <E> - Tipo que é recebido, podendo ser qualquer <i>sub-classe</i> de {@link Objeto}.
 * @version 1.0.0
 */
public abstract class Generico<E extends Objeto> extends Objeto {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Método responsável por verificar se um  campo possui uma determinada anotação.
	 * @author Wesley Luiz
	 * @param field - Campo a ser analisado.
	 * @param anotacao - Anotação do campo.
	 * @return Retorna <code>true</code> caso possua anotação ou <code>false</code> caso não tenha.
	 */
	protected boolean isPossuiAnotacao(final Field field, final Class<? extends Annotation> anotacao) {
		return field.isAnnotationPresent(anotacao);
	}
	
	/**
	 * Método responsável por obter os atributos de uma <code>Entidade</code>.
	 * @author Wesley Luiz
	 * @param entidade - Recebe a <code>Entidade</code> que possui os atributos.
	 * @return Retorna um <i>array</i> de atributos.
	 */
	protected Field[] obterCamposEntidade(final Entidade entidade) {
		return entidade.getClass().getDeclaredFields();
	}

	/**
	 * Método responsável por invocar um método de uma determinado <code>Entidade</code>.
	 * @author Wesley Luiz
	 * @param nomeMetodo - Recebe o nome do método que irá ser invocado.
	 * @param entidade - Recebe a classe que possui o método. Essa classe deve ser uma <code>Entidade</code>.
	 * @return Retorna o objeto obtido do método executado.
	 */
	protected Object invocarMetodo(final String nomeMetodo, final Entidade entidade) {
		try {
			final Method method = entidade.getClass().getMethod(nomeMetodo);
			return method.invoke(entidade);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método responsável por obter o tipo de classe que está sendo passada como
	 * <i>Generics</i>, essa classe pode ser qualquer uma que extenda {@link Objeto}.
	 * <br>
	 * 
	 * @param index - recebe o <i>index</i> do <i>array</i> de classes.
	 * @author Wesley Luiz
	 * @return Retorna o tipo da <i>Classe</i> obtida.
	 * @see Class
	 */
	@SuppressWarnings({ "unchecked", "restriction" })
	protected Class<E> obterTipoDaClasse(final int index) {
		return (Class<E>) ((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}

	/**
	 * Método responsável por obter o tipo de classe que está sendo passada como
	 * <i>Generics</i>, essa classe pode ser qualquer uma que extenda {@link Objeto}.
	 * <br>
	 * 
	 * @author Wesley Luiz
	 * @return Retorna o tipo da <i>Classe</i> obtida.
	 * @see Class
	 */
	@SuppressWarnings({ "unchecked", "restriction" })
	protected Class<E> obterTipoDaClasse() {
		return (Class<E>) ((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
