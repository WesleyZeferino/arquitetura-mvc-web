package br.com.arquitetura.bundle;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import br.com.arquitetura.objeto.Objeto;

/**
 * Esta é uma classe <i>final</i> responsável por fornecer métodos que trabalhem com <code>ResourceBundle</code>,<br> 
 * oferecendo recursos de internacionalização apartir de arquivos de mensagems <i>(.properties)</i>. <br>
 * Exemplo de uso:
 * <pre>
 * protected static String getMensagem(final String key) {
 * 	return Bundle.getResourceBundle(new Locale("pt", "BR")).getString(key);
 * }
 * </pre>
 * @author Wesley Luiz
 * @version 1.0.0
 * @see ResourceBundle
 */
public final class Bundle extends Objeto {

	private final static long serialVersionUID = 1L;
	private final static String BASE_NAME = "mensagem";
	
	public final static String LANGUAGE_PT = "pt";
	public final static String LANGUAGE_EN = "en";
	public final static String COUNTRY_BR = "BR";
	public final static String COUNTRY_US = "US";
	
	public final static Severity GRAVIDADE_ERRO = FacesMessage.SEVERITY_ERROR;
	public final static Severity GRAVIDADE_FATAL = FacesMessage.SEVERITY_FATAL;
	public final static Severity GRAVIDADE_INFORMATIVO = FacesMessage.SEVERITY_INFO;
	public final static Severity GRAVIDADE_ALERTA = FacesMessage.SEVERITY_WARN;

	private static Bundle sigleton;
	private static ResourceBundle bundle;
	
	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 */
	protected Bundle() {
		super();
	}
	
	/**
	 * Método responsável por obter um objeto do tipo <code>ResourceBundle</code>.
	 * @author Wesley Luiz
	 * @param locale - Recebe um objeto do tipo <code>Locale</code> que é uma representação geográfica específica.
	 * @return Retorna um objeto do tipo <code>ResourceBundle</code> apartir de um <i>base name</i>.
	 * @see Locale
	 * @see ResourceBundle
	 */
	public static ResourceBundle getResourceBundle(final Locale locale) {
		if (!isReferencia(bundle)) {
			bundle = ResourceBundle.getBundle(BASE_NAME, locale);
		}
		return bundle;
	}

	/**
	 * Método responsável por obter uma única instância dessa classe.
	 * @author Wesley Luiz
	 * @return Retorna um objeto da própria classe.
	 */
	public static Bundle getInstance() {
		return (isReferencia(sigleton) ? sigleton : new Bundle());
	}
}
