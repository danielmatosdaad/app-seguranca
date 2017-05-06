package br.app.barramento.seguranca.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.app.barramento.infra.persistencia.dao.AbstractFacade;
import br.app.barramento.seguranca.dao.model.ParametroSeguranca;

@Stateless
public class ParametroSegurancaFacede extends AbstractFacade<ParametroSeguranca> {

	@PersistenceContext(unitName = "app-contexto-seguranca")
	private EntityManager em;

	public ParametroSegurancaFacede() {
		super(ParametroSeguranca.class);
	}

	public ParametroSegurancaFacede(Class<ParametroSeguranca> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<ParametroSeguranca> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ParametroSeguranca> criteria = cb.createQuery(ParametroSeguranca.class);
		Root<ParametroSeguranca> root = criteria.from(ParametroSeguranca.class);
		CriteriaQuery<ParametroSeguranca> todos = criteria.select(root);
		TypedQuery<ParametroSeguranca> allQuery = em.createQuery(todos);

		List<ParametroSeguranca> resultado = allQuery.getResultList();

		System.out.println("Quantidade todos? " + resultado.size());
		return resultado;
	}
}
