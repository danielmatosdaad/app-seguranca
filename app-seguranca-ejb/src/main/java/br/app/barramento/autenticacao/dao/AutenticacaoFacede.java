package br.app.barramento.autenticacao.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.app.barramento.autenticacao.dao.model.Autenticacao;
import br.app.barramento.infra.persistencia.dao.AbstractFacade;

@Stateless
public class AutenticacaoFacede extends AbstractFacade<Autenticacao> {

	@PersistenceContext(unitName = "app-contexto-seguranca")
	private EntityManager em;

	public AutenticacaoFacede() {
		super(Autenticacao.class);
	}

	public AutenticacaoFacede(Class<Autenticacao> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<Autenticacao> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Autenticacao> criteria = cb.createQuery(Autenticacao.class);
		Root<Autenticacao> root = criteria.from(Autenticacao.class);
		CriteriaQuery<Autenticacao> todos = criteria.select(root);
		TypedQuery<Autenticacao> allQuery = em.createQuery(todos);

		List<Autenticacao> resultado = allQuery.getResultList();

		System.out.println("Quantidade todos? " + resultado.size());
		return resultado;
	}

	public Autenticacao buscarPorNomeAutenticacao(String nome) {
		return null;
	}
}
