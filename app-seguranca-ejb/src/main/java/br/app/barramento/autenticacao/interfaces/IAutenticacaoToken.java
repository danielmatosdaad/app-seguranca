package br.app.barramento.autenticacao.interfaces;

import br.app.barramento.autenticacao.dto.AutenticacaoTokenEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoTokenResposta;
import br.app.barramento.autenticacao.dto.ReautenticacaoTokenEnvio;
import br.app.barramento.autenticacao.dto.ReautenticacaoTokenResposta;

public interface IAutenticacaoToken {

	public AutenticacaoTokenResposta autenticar(AutenticacaoTokenEnvio autenticacaoEnvio);

	public ReautenticacaoTokenResposta reautenticar(ReautenticacaoTokenEnvio reautenticacaoTokenEnvio);
}
