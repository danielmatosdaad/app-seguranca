package br.app.barramento.autenticacao.imp;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.app.barramento.autenticacao.dto.AutenticacaoTokenEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoTokenResposta;
import br.app.barramento.autenticacao.dto.ReautenticacaoTokenEnvio;
import br.app.barramento.autenticacao.dto.ReautenticacaoTokenResposta;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoToken;

@Stateless
@Named
public class AutenticacaoTokenServiceImp implements IAutenticacaoToken{

	@Override
	public AutenticacaoTokenResposta autenticar(AutenticacaoTokenEnvio autenticacaoEnvio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReautenticacaoTokenResposta reautenticar(ReautenticacaoTokenEnvio reautenticacaoTokenEnvio) {
		// TODO Auto-generated method stub
		return null;
	}

}
