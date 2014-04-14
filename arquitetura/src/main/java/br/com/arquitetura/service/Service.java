package br.com.arquitetura.service;

import java.lang.reflect.Field;
import java.util.List;
import javax.faces.context.FacesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import br.com.arquitetura.annotation.NaoVazio;
import br.com.arquitetura.bundle.Bundle;
import br.com.arquitetura.dao.HibernateDAO;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.exception.CampoVazioException;
import br.com.arquitetura.objeto.Generico;
import br.com.arquitetura.util.Texto;

/**
 * Fornece implementação das regras de negócio de um caso de uso, pertence a
 * camada de <i>Serviço</i>
 * 
 * @author Wesley Luiz
 * @version 1.0.0
 */
@org.springframework.stereotype.Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class Service<T extends HibernateDAO<E>, E extends Entidade> extends Generico<E> {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Atributo MSG_CAMPOS_OBRIGATORIOS. */
	protected static final String MSG_CAMPOS_OBRIGATORIOS = "arquitetura.msg.camposObrigatorios";

	/** Atributo MSG_DADO_DUPLICADO. */
	protected static final String MSG_DADO_DUPLICADO = "arquitetura.msg.dadoDuplicado";

	/** Atributo MSG_SALVAR_SUCESSO. */
	protected static final String MSG_SALVAR_SUCESSO = "arquitetura.msg.salvar.sucesso";

	/** Atributo dao. */
	protected HibernateDAO<E> dao;

	/**
	 * Método responsável por validar os campos de preenchimento obrigatório da <code>Entidade</code>.
	 * @author Wesley Luiz
	 * @param entidade - Recebe a <code>Entidade</code> que será validada.
	 * @throws CampoVazioException Lança uma exceção caso haja campos obrigatórios vazios.
	 */
	public void validarCamposVazios(final Entidade entidade) throws CampoVazioException {
		for (final Field field : obterCamposEntidade(entidade)) {
			if (isPossuiAnotacao(field, NaoVazio.class)) {

				if (isVazio(invocarMetodo("get" + Texto.toPrimeiraLetraMaiuscula(field.getName()), entidade).toString())) {
					throw new CampoVazioException(field.getName());
				}
			}
		}
	}

	/**
	 * Método responsável por exibir uma mensagem de alerta na tela, dizendo que existe(m) campo(s) de preenchimento obrigatório vazio(s).
	 * @param e - Recebe a exceção lançada.
	 * @author Wesley Luiz
	 */
	protected void exibirMensagemCamposObrigatorios(final CampoVazioException e) {
		exibirMensagem(Bundle.GRAVIDADE_ALERTA, FacesContext.getCurrentInstance(), getMensagem(MSG_CAMPOS_OBRIGATORIOS), e.getMessage());
	}

	/**
	 * Método responsável por chamar o método responsável por persistir um objeto do tipo
	 * <code>Entidade</code> na camada de <i>persistência</i> o chamado <i>DAO</i>.
	 * @author Wesley Luiz
	 * @param entidade - Recebe uma instância de <code>Entidade</code>.
	 */
	public void salvar(final E entidade) {
		getDao().salvar(entidade);
	}

	/**
	 * Método responsável por chamar o método responsável por alterar um objeto do tipo
	 * <code>Entidade</code> na camada de <i>persistência</i> o chamado <i>DAO</i>.
	 * @author Wesley Luiz
	 * @param entidade - Recebe uma instância de <code>Entidade</code>.
	 */
	public void alterar(final E entidade) {
		getDao().alterar(entidade);
	}

	/**
	 * Método responsável por chamar o método reponsável por salvar ou alterar um objeto no DAO.
	 * @author Wesley Luiz
	 * @param entidade - Recebe uma instância de <code>Entidade</code>.
	 */
	public void salvarOuAlterar(final E entidade) {
		getDao().salvarOuAlterar(entidade);
	}

	/**
	 * Método responsável por chamar o método responsável por remover um objeto do tipo
	 * <code>Entidade</code> na camada de <i>persistência</i> o chamado <i>DAO</i>.
	 * @author Wesley Luiz
	 * @param entidade - Recebe uma instância de <code>Entidade</code>.
	 */
	public void remover(final E entidade) {
		getDao().remover(entidade);
	}

	/**
	 * Método responsável por buscar um objeto no <i>Banco de Dados</i> a partir do <i>id</i> da <code>Entidade</code>.
	 * @author Wesley Luiz
	 * @param id - Identificador da {@link Entidade} que deve ser pesquisada no <i>Banco de Dados</i>.
	 * @return Retorna o objeto encontrado.
	 */
	public E obterPorId(final Integer id) {
		return getDao().obterPorId(id);
	}

	/**
	 * Método responsável por buscar uma lista objetos no <i>Banco de Dados</i>.
	 * @author Wesley Luiz
	 * @return Retorna uma lista ({@link List}) de objetos contendo todos os registros no <i>Banco de Dados</i> referente a <i>tabela</i> em questão.
	 */
	public List<E> listar() {
		return getDao().listar();
	}

	/**
	 * Retorna o valor do atributo <code>dao</code>
	 * @return <code>HibernateDAO<E></code>
	 */
	protected abstract HibernateDAO<E> getDao();
}
