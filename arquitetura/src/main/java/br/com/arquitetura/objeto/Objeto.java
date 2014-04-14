package br.com.arquitetura.objeto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import br.com.arquitetura.bundle.Bundle;

/**
 * Classe responsável por fornecer suporte à outras classes que extendê-la. Essa é a <i>super-classe</i>
 * de toda <i>classe</i> pertecente a <i>arquitetura</i>,<br>
 * ou seja, toda <i>sub-classe</i> dela terá alguns recursos básicos como por exemplo verificar um
 * <i>objeto</i> <code>null</code>.
 * @author Wesley Luiz
 * @version 1.0.0
 */
public abstract class Objeto implements Serializable {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método responsável por exibir uma mensagem na tela após alguma operação que seja necessário retornar alguma informação ao usuário.
	 * 
	 * <pre>
	 * public void exemploMensagem() {
	 *	exibirMensagem(FacesContext.getCurrentInstance(), "msg.exemplo");
	 * }</pre>
	 * 
	 * @author Wesley Luiz
	 * @param context - Instância de <code>FacesContext</code>.
	 * Essa instância deve ser obtida da seguinte forma <pre>exibirMensagem(FacesContext.getCurrentInstance(), "mensagem");</pre>
	 * @param msg - <code>String</code> que pela qual leva a mensagem que vai ser apresentada na tela.
	 */
	protected static void exibirMensagem(final FacesContext context, final String msg) {
		context.addMessage(null, new FacesMessage(msg, null));
	}

	/**
	 * Método responsável por exibir uma mensagem na tela após alguma operação que seja necessário retornar alguma informação ao usuário. Exemplo:
	 * 
	 * <pre>
	 * public void exemploMensagem() {
	 *	exibirMensagem(Bundle.GRAVIDADE_INFORMATIVO, FacesContext.getCurrentInstance(), "msg.exemplo");
	 * }</pre>
	 * 
	 * @author Wesley Luiz
	 * @param severity - Objeto pela qual exibe a gravidade da mensagem retornada.
	 * @param context - Instância de <code>FacesContext</code>.
	 * @param msg - <code>String</code> que pela qual leva a mensagem que vai ser apresentada na tela.
	 * @see Severity
	 */
	protected static void exibirMensagem(final Severity severity, final FacesContext context, final String msg) {
		context.addMessage(null, new FacesMessage(severity, msg, null));
	}

	/**
	 *  Método responsável por exibir uma mensagem na tela após alguma operação que seja necessário retornar alguma informação ao usuário. Exemplo:
	 * 
	 * <pre>
	 * public void exemploMensagem() {
	 *	exibirMensagem(Bundle.GRAVIDADE_INFORMATIVO, FacesContext.getCurrentInstance(), "Mensagem Summary", "Mensagem Detail");
	 * }</pre>
	 * 
	 * @author Wesley Luiz
	 * @param severity - Objeto pela qual exibe a gravidade da mensagem retornada.
	 * @param context - Instância de <code>FacesContext</code>.
	 * @param summary - <code>String</code> que pela qual leva a mensagem que vai ser apresentada na tela.
	 * @param detail - Detalhes da mensagem.
	 * @see Severity
	 */
	protected static void exibirMensagem(final Severity severity, final FacesContext context, final String summary, final String detail) {
		context.addMessage(null, new FacesMessage(severity, summary, detail));
	}

	/**
	 * Método responsável por obter uma mensagem de um arquivo de mensagens apartir de uma <b>chave</b>.<br>
	 * Essa chave nada mais é do que a referencia a uma determinada mensagem do arquivo <i>.properties</i>.
	 * @author Wesley Luiz
	 * @param key - Recebe uma <code>String</code> referente a chave da mensagem do arquivo de mensagens.
	 * @return Retorna um objeto do tipo <code>String</code> com a mensagem obtida.
	 */
	protected String getMensagem(final String key) {
		return isReferencia(key) ? Bundle.getResourceBundle(new Locale(Bundle.LANGUAGE_PT, Bundle.COUNTRY_BR)).getString(key) : null;
	}

	/**
	 * Método responsável por validar um <i>objeto</i> caso possua referência na memória.
	 * @author Wesley Luiz
	 * @param valor é a variável à ser validada.
	 * @return Retorna <i>true</i> se possuir referência ou <i>false</i> caso não tenha.
	 */
	protected static Boolean isReferencia(final Object valor) {
		return valor != null;
	}

	/**
	 * Método responsável por validar uma <i>String</i> caso seja vazia ou não.
	 * @author Wesley Luiz
	 * @param valor à ser validado.
	 * @return Retorna um <i>boolean true</i> caso a variável seja vazia ou <i>false</i> caso não seja.
	 */
	protected static Boolean isVazio(final Object valor) {
		return isReferencia(valor) ? valor.toString().trim().isEmpty() : true;
	}

	/**
	 * Método responsável por validar uma <i>Coleção</i> caso seja vazia ou não.
	 * @author Wesley Luiz
	 * @param lista à ser validada.
	 * @return Retorna um <i>boolean true</i> caso a lista seja vazia ou <i>false</i> caso não seja.
	 */
	protected static Boolean isVazio(final Collection<? extends Objeto> lista) {
		return isReferencia(lista) ? lista.isEmpty() : true;
	}

	/**
	 * Método responsável por obter o tamanho atual de uma <i>lista</i> de <code>Objeto</code>(s).
	 * @author Wesley Luiz
	 * @param lista - Recebe a lista à ser verificada.
	 * @return Retorna um objeto do tipo <code>Integer</code>
	 * @see Objeto
	 */
	protected static Integer getTamanho(final Collection<? extends Objeto> lista) {
		return !isVazio(lista) ? lista.size() : 0;
	}
}
