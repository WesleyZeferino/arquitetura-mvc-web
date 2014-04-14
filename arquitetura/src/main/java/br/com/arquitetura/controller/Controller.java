package br.com.arquitetura.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.exception.ConstraintViolationException;
import br.com.arquitetura.bundle.Bundle;
import br.com.arquitetura.dao.HibernateDAO;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.enumerator.EnumStatus;
import br.com.arquitetura.exception.CampoVazioException;
import br.com.arquitetura.objeto.Generico;
import br.com.arquitetura.objeto.Objeto;
import br.com.arquitetura.service.Service;
import br.com.arquitetura.util.UtilReports;

/**
 * Classe abstrata pertecente a camada de visão, reponsável por fornecer apoio ao <i>ManagedBean</i> que a implementa.<br>
 * Nesta classe é instânciada a <i>Entidade</i> que está sendo trabalhada no momento.
 * 
 * @author Wesley Luiz
 * @version 1.0.0
 */
public abstract class Controller<S extends Service<D, E>, D extends HibernateDAO<E>, E extends Entidade> extends Generico<E> {

	/** Atributo SPACE. */
	private static final String SPACE = " ";

	/** Atributo MSG_CAMPOS_OBRIGATORIOS. */
	protected static final String MSG_CAMPOS_OBRIGATORIOS = "arquitetura.msg.camposObrigatorios";

	/** Atributo MSG_DADO_DUPLICADO. */
	protected static final String MSG_DADO_DUPLICADO = "arquitetura.msg.dadoDuplicado";

	/** Atributo MSG_SALVAR_SUCESSO. */
	protected static final String MSG_SALVAR_SUCESSO = "arquitetura.msg.salvar.sucesso";

	/** Atributo MSG_EDITAR_SUCESSO. */
	protected static final String MSG_EDITAR_SUCESSO = "arquitetura.msg.editar.sucesso";

	/** Atributo MSG_REMOVER_SUCESSO. */
	protected static final String MSG_REMOVER_SUCESSO = "arquitetura.msg.remover.sucesso";

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Atributo entidade. */
	private E entidade;

	/** Atributo service. */
	protected Service<D, E> service;

	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 * @param clazz
	 */
	public Controller(final Class<E> clazz) {
		try {
			entidade = clazz.newInstance();
			entidade.setStatus(EnumStatus.ATIVO);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 */
	protected <T> Controller() {
		iniciarDados();
	}

	/**
	 * Método responsável por obter uma nova instância da <code>Entidade</code> que está sendo trabalhada no momento.<br>
	 * Se o método de inclusão dessa classe for chamado este "restart" e efetuado automaticamente.
	 * @author Wesley Luiz
	 */
	protected void iniciarDados() {
		try {
			entidade = obterTipoDaClasse(2).newInstance();
			entidade.setStatus(EnumStatus.ATIVO);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método responsável por identificar o que causou a exceção no momento em que foi persistir os dados.
	 * @author Wesley Luiz
	 * @param e Recebe a exceção lançada.
	 * @return Retorna uma <code>String</code> contendo a mensagem de exceção.
	 */
	private String obterCausaExcecao(final ConstraintViolationException e) {
		if (e.getMessage().contains("Duplicate")) {
			return getMensagem(MSG_DADO_DUPLICADO).concat(obterCampoDeExcecao(e, 2));
		} else if (e.getMessage().contains("cannot be null")) {
			return getMensagem(MSG_CAMPOS_OBRIGATORIOS);
		} else {
			return e.getMessage();
		}
	}

	/**
	 * Método responsável por obter o atributo da <code>Entidade</code> que provocou a exceção.
	 * @author Wesley Luiz
	 * @param e Recebe a exceção lançada.
	 * @param index Recebe o <i>index</i> da palavra que contém o nome do atributo.
	 * @return Retorna o nome do atributo.
	 */
	private String obterCampoDeExcecao(final ConstraintViolationException e, final int index) {
		return SPACE + e.getMessage().split(SPACE)[index];
	}

	/**
	 * Método responsável por chamar o método da camada de <i>Serviço</i>
	 * responsável por fazer a persitência do objeto do tipo <code>Entidade</code>.<br>
	 * @author Wesley Luiz
	 * @see Entidade
	 */
	public void salvar() {
		try {
			getService().validarCamposVazios(getEntidade());
			getService().salvar(getEntidade());
			iniciarDados();
			exibirMensagem(FacesContext.getCurrentInstance(), getMensagem(MSG_SALVAR_SUCESSO));
		} catch (final CampoVazioException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), getMensagem(MSG_CAMPOS_OBRIGATORIOS), e.getMessage());
		} catch (final ConstraintViolationException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), obterCausaExcecao(e));
		}
	}

	/**
	 * Método responsável por chamar o método da camada de <i>Serviço</i>
	 * responsável por fazer a alteração do objeto do tipo <code>Entidade</code>.<br>
	 * @author Wesley Luiz
	 * @see Entidade
	 */
	public void alterar() {
		try {
			getService().validarCamposVazios(getEntidade());
			getService().alterar(getEntidade());
			iniciarDados();
			exibirMensagem(FacesContext.getCurrentInstance(), getMensagem(MSG_EDITAR_SUCESSO));
		} catch (final CampoVazioException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), getMensagem(MSG_CAMPOS_OBRIGATORIOS), e.getMessage());
		} catch (final ConstraintViolationException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), obterCausaExcecao(e));
		}
	}

	/**
	 * Método responsável por chamar o método da camada de <i>Serviço</i>
	 * responsável por salvar ou alterar um objeto do tipo <code>Entidade</code>.<br>
	 * @author Wesley Luiz
	 * @see Entidade
	 */
	public void salvarOuAlterar() {
		try {
			getService().validarCamposVazios(getEntidade());
			getService().salvarOuAlterar(getEntidade());
			iniciarDados();
			exibirMensagem(FacesContext.getCurrentInstance(), getMensagem(MSG_SALVAR_SUCESSO));
		} catch (final CampoVazioException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), getMensagem(MSG_CAMPOS_OBRIGATORIOS), e.getMessage());
		} catch (final ConstraintViolationException e) {
			exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), obterCausaExcecao(e));
		}
	}

	/**
	 * Método responsável por chamar o método da camada de <i>Serviço</i>
	 * responsável por fazer a remoção do objeto do tipo <code>Entidade</code>.<br>
	 * @author Wesley Luiz
	 * @see Entidade
	 */
	public void remover() {
		getService().remover(getEntidade());
		iniciarDados();
		exibirMensagem(FacesContext.getCurrentInstance(), getMensagem(MSG_REMOVER_SUCESSO));
	}

	/**
	 * Método responsável por chamar o método da camada de <i>Serviço</i> reponsável por buscar um objeto
	 * no <i>Banco de Dados</i> a partir do <i>id</i> da <code>Entidade</code>.
	 * @author Wesley Luiz
	 * @param id - Identificador da {@link Entidade} que deve ser pesquisada no <i>Banco de Dados</i>.
	 * @return Retorna o objeto encontrado.
	 * @see Entidade
	 */
	public E obterPorId(final Integer id) {
		return getService().obterPorId(id);
	}

	/**
	 * Método responsável por gerar relatórios no formato <i>.pdf</i>  de arquivos <i>JasperReports</i>.
	 * @author Wesley Luiz
	 * @param dataSource - Recebe uma coleção contendo os dados do relatório. Deve ser uma coleção de objetos que <i>extenda</i> de <code>Objeto</code>.
	 * @param parametros - <code>Map</code> que recebe os parâmetros do relatório.
	 * @param jasperFileName - <code>String</code> contendo o nome do arquivo <i>(.jasper)</i>.
	 * @param context - Recebe uma instância do contexto corrente da aplicação.
	 * @see Map
	 * @see Objeto
	 * @see Collection
	 */
	protected void gerarRelatorioPDF(final Collection<? extends Objeto> dataSource, final Map<String, Object> parametros, final String jasperFileName, final FacesContext context) {
		try {
			UtilReports.gerarRelatorioPDF(dataSource, parametros, jasperFileName, context, false);
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o valor do atributo <code>entidade</code>
	 * @return <code>E</code>
	 */
	public E getEntidade() {
		return entidade;
	}

	/**
	 * Define o valor do atributo <code>entidade</code>.
	 * @param entidade
	 */
	public void setEntidade(final E entidade) {
		this.entidade = entidade;
	}

	/**
	 * Retorna o valor do atributo <code>service</code>
	 * @return <code>Service<D,E></code>
	 */
	public abstract Service<D, E> getService();
}
