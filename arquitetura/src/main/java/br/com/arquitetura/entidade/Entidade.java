package br.com.arquitetura.entidade;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import br.com.arquitetura.dao.DAO;
import br.com.arquitetura.dao.HibernateDAO;
import br.com.arquitetura.enumerator.EnumStatus;
import br.com.arquitetura.objeto.Objeto;
import br.com.arquitetura.service.Service;

/**
 * Esta classe tem por finalidade fornecer atributos básicos de uma entidade que
 * é persistida num <code>Banco de Dados.</code><br>
 * As entidades que forem persistidas utilizando {@link HibernateDAO} deverão
 * extender esta classe.<br>
 * <br>
 * 
 * @author Wesley Luiz
 * @version 1.0.0
 * @see DAO
 * @see HibernateDAO
 * @see Service
 */
@MappedSuperclass
public class Entidade extends Objeto {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.ORDINAL)
	private EnumStatus status;

	/**
	 * Método responsável por obter o valor do status da <i>entidade</i> na forma <i>booleana</i>.
	 * 
	 * @author Wesley Luiz
	 * @return Retorna um <code>Boolean</code> referente ao status da <i>entidade</i>.
	 */
	public boolean obterStatus() {
		return this.getStatus().getStatus();
	}

	/**
	 * Retorna o valor do atributo <code>id</code>
	 * @return <code>int</code>
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Define o valor do atributo <code>id</code>.
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Retorna o valor do atributo <code>status</code>
	 * @return <code>EnumStatus</code>
	 */
	public EnumStatus getStatus() {
		return status;
	}

	/**
	 * Define o valor do atributo <code>status</code>.
	 * @param status
	 */
	public void setStatus(final EnumStatus status) {
		this.status = status;
	}
}
