package br.com.arquitetura.dao;

import java.util.List;
import br.com.arquitetura.entidade.Entidade;

/**
 * 
 * Interface responsável por definir o contrato de implementação <code>Data Access Object (DAO)</code>.<br>
 * As classes que implementarem essa <code>interface</code> devem sobrescrever os principais métodos reponsáveis por definir um <code>CRUD</code>. 
 * 
 * @author Wesley Luiz
 * @version 1.0.0
 */
public interface DAO<E extends Entidade> {

	/**
	 * Método responsável por persistir um objeto em sua determinada <i>tabela</i> no <i>Banco de Dados</i>.
	 * 
	 * @author Wesley Luiz
	 * @param entidade - Objeto referente a classe que deve ser persistida no <i>Banco de Dados</i>.
	 */
	void salvar(final E entidade);

	/**
	 * Método responsável por alterar um objeto em sua determinada <i>tabela</i> no <i>Banco de Dados</i>.
	 * 
	 * @author Wesley Luiz
	 * @param entidade - Objeto referente a classe que deve ser alterada no <i>Banco de Dados</i>.
	 */
	void alterar(final E entidade);

	/**
	 * Método responsável por persistir ou alterar um objeto em sua determinada <i>tabela</i> no <i>Banco de Dados</i>. <br> O Framework irá persistir se o objeto tiver sua 
	 * <i>chave primária (PK)</i> como <code>null</code> e alterar a entidade se o objeto possuir uma <i>chave primária.</i>
	 * 
	 * @author Wesley Luiz
	 * @param entidade - Objeto referente a classe que deve ser persistida ou alterada no <i>Banco de Dados</i>. 
	 */
	void salvarOuAlterar(final E entidade);

	/**
	 * Método responsável por remover um objeto de sua determinada <i>tabela</i> no <i>Banco de Dados</i>.
	 * 
	 * @author Wesley Luiz
	 * @param entidade - Objeto referente a classe que deve ser removida do <i>Banco de Dados</i>.
	 */
	void remover(final E entidade);

	/**
	 * Método responsável por buscar um objeto no <i>Banco de Dados</i> a partir do <i>id</i> da <code>Entidade</code>.
	 * 
	 * @author Wesley Luiz
	 * @param id - Identificador da {@link Entidade} que deve ser pesquisada no <i>Banco de Dados</i>.
	 * @return Retorna o objeto encontrado.
	 */
	E obterPorId(final Integer id);

	/**
	 * Método responsável por buscar uma lista objetos no <i>Banco de Dados</i>.
	 * 
	 * @author Wesley Luiz
	 * @return Retorna uma lista ({@link List}) de objetos contendo todos os registros no <i>Banco de Dados</i> referente a <i>tabela</i> em questão.
	 */
	List<E> listar();
}
