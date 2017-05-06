package br.app.barramento.autorizacao.imp;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Named;

import br.app.barramento.autorizacao.dto.AutorizacaoServicoEnvio;
import br.app.barramento.autorizacao.dto.AutorizacaoServicoResposta;
import br.app.barramento.autorizacao.interfaces.IAutorizacaoServiceLocal;
import br.app.barramento.autorizacao.interfaces.IAutorizacaoServiceRemote;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;

@Stateless
@Named
@Remote({ IAutorizacaoServiceRemote.class })
@Local({ IAutorizacaoServiceLocal.class })
public class AutorizacaoServiceImp implements IAutorizacaoServiceRemote, IAutorizacaoServiceLocal {

	@Override
	public AutorizacaoServicoResposta autorizacao(AutorizacaoServicoEnvio autorizacao)
			throws InfraEstruturaException, NegocioException {

		AutorizacaoServicoResposta respostaServicoAutorizado = new AutorizacaoServicoResposta();

		return respostaServicoAutorizado;
	}

}
