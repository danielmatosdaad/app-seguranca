package br.app.barramento.autenticacao.interfaces;

import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoResposta;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;

public interface IAutenticacao {

	public AutenticacaoResposta autenticar(AutenticacaoEnvio autenticacaoEnvio) throws InfraEstruturaException,NegocioException;
}
