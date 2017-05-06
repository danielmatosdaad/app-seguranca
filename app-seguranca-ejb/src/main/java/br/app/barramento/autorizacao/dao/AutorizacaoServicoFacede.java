package br.app.barramento.autorizacao.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.app.barramento.autorizacao.dao.model.AutorizacaoServico;
import br.app.barramento.infra.persistencia.dao.AbstractFacade;

@Stateless
public class AutorizacaoServicoFacede extends AbstractFacade<AutorizacaoServico> {

	@PersistenceContext(unitName = "app-contexto-seguranca")
	private EntityManager em;

	public AutorizacaoServicoFacede() {
		super(AutorizacaoServico.class);
	}

	public AutorizacaoServicoFacede(Class<AutorizacaoServico> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<AutorizacaoServico> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AutorizacaoServico> criteria = cb.createQuery(AutorizacaoServico.class);
		Root<AutorizacaoServico> root = criteria.from(AutorizacaoServico.class);
		CriteriaQuery<AutorizacaoServico> todos = criteria.select(root);
		TypedQuery<AutorizacaoServico> allQuery = em.createQuery(todos);

		List<AutorizacaoServico> resultado = allQuery.getResultList();

		System.out.println("Quantidade todos? " + resultado.size());
		return resultado;
	}
}
