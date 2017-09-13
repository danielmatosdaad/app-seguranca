package org.app.seguranca.integracao;

import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.integracao.dto.EnvioDTO;
import br.app.barramento.integracao.dto.RespostaDTO;
import br.app.barramento.integracao.dto.TipoAcao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;

public interface IProxySegurancaConexao {

	public String autenticacaoAutorizacao(AutenticacaoEnvio autenticacaoEnvio)
			throws NegocioException, InfraEstruturaException;

	public RespostaDTO executar(TipoAcao acao, EnvioDTO envio) throws NegocioException, InfraEstruturaException;
}
