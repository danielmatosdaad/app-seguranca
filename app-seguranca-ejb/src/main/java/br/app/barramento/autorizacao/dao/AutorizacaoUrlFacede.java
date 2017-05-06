package br.app.barramento.autorizacao.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.app.barramento.autorizacao.dao.model.AutorizacaoUrl;
import br.app.barramento.infra.persistencia.dao.AbstractFacade;

@Stateless
public class AutorizacaoUrlFacede extends AbstractFacade<AutorizacaoUrl> {

	@PersistenceContext(unitName = "app-contexto-seguranca")
	private EntityManager em;

	public AutorizacaoUrlFacede() {
		super(AutorizacaoUrl.class);
	}

	public AutorizacaoUrlFacede(Class<AutorizacaoUrl> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<AutorizacaoUrl> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AutorizacaoUrl> criteria = cb.createQuery(AutorizacaoUrl.class);
		Root<AutorizacaoUrl> root = criteria.from(AutorizacaoUrl.class);
		CriteriaQuery<AutorizacaoUrl> todos = criteria.select(root);
		TypedQuery<AutorizacaoUrl> allQuery = em.createQuery(todos);

		List<AutorizacaoUrl> resultado = allQuery.getResultList();

		System.out.println("Quantidade todos? " + resultado.size());
		return resultado;
	}
}
