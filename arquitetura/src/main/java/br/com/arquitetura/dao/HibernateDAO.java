package br.com.arquitetura.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.objeto.Generico;

/**
 * Pertecente à camada de <i>Persistência</i> esta classe é responsável por
 * fornecer acesso e manipulação de dados em um determinado <code>Banco de Dados.</code> <br>
 * As classes que à extenderem terão todas as funções básicas para implementar um <i>CRUD (Create Read Update and Delete)</i>.
 * 
 * @author Wesley Luiz
 * @param E - Recebe uma classe que <i>extende</i> {@link Entidade}.
 * @version 1.0.0
 */
public abstract class HibernateDAO<E extends Entidade> extends Generico<E> implements DAO<E> {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Atributo entityManager. */
	protected EntityManager entityManager;

	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 */
	public HibernateDAO() {
		super();
	}
	
	/**
	 * Responsável pela criação de novas instâncias desta classe.
	 * 
	 * @param entityManager - Recebe um objeto da <i>entidade</i> gerenciadora <i>JPA</i> <code>EntityManager</code>.
	 */
	public HibernateDAO(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Método responsável por obter uma instância de <code>EntityManager</code>.
	 *
	 * @author Wesley Luiz
	 * @return Retorna uma instância de <code>EntityManager</code>.
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Método responsável por obter uma instância da <i>Sessão</i> do
	 * <code>Hibernate</code> através do método <code>getDelegate()</code>.
	 * 
	 * @author Wesley Luiz
	 * @return Retorna uma <i>Sessão</i> do <code>Hibernate</code>.
	 * @see Session
	 */
	protected Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}

	/**
	 * Método responsável por criar uma nova instância da <i>interface</i>
	 * <code>Criteria</code> do <code>Hibernate</code>, atravéz da
	 * {@link Entidade} em questão.
	 * 
	 * Exemplo:
	 * <pre>
	 * public void listarTudo() {
     * 	Criteria criteria = this.novoCriteria();
	 * }</pre>
	 * 
	 * @author Wesley Luiz
	 * @return Retorna uma isntância de <code>Criteria</code>.
	 * @see Criteria
	 * @see Session
	 */
	public Criteria novoCriteria() {
		return getSession().createCriteria(obterTipoDaClasse());
	}

	@Override
	public void salvar(final E entidade) {
		getSession().save(entidade);
		getSession().flush();
	}

	@Override
	public void alterar(final E entidade) {
		getSession().update(entidade);
		getSession().flush();
	}

	@Override
	public void salvarOuAlterar(final E entidade) {
		getSession().saveOrUpdate(entidade);
		getSession().flush();
	}

	@Override
	public void remover(final E entidade) {
		getSession().load(entidade, entidade.getId());
		getSession().delete(entidade);
		getSession().flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E obterPorId(final Integer id) {
		return (E) novoCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> listar() {
		return novoCriteria().list();
	}
}
