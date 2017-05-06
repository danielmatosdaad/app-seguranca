package br.app.barramento.autorizacao.interfaces;

import br.app.barramento.autorizacao.dto.AutorizacaoServicoEnvio;
import br.app.barramento.autorizacao.dto.AutorizacaoServicoResposta;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;

public interface IAutorizacaoService {

	public AutorizacaoServicoResposta autorizacao(AutorizacaoServicoEnvio autorizacao)
			throws InfraEstruturaException, NegocioException;
}
