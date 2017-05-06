package br.app.barramento.autorizacao.dto;

import java.io.Serializable;

import br.app.barramento.autenticacao.dto.AutenticacaoResposta;

public class AutenticacaoAutorizacaoResposta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AutenticacaoResposta autenticacaoResposta;
	private AutorizacaoServicoResposta autorizacaoServicoResposta;

	public AutenticacaoAutorizacaoResposta(AutenticacaoResposta autenticacaoResposta,
			AutorizacaoServicoResposta autorizacaoServicoResposta) {

		this.autenticacaoResposta = autenticacaoResposta;
		this.autorizacaoServicoResposta = autorizacaoServicoResposta;
	}

	public AutenticacaoAutorizacaoResposta() {
	}

	public AutenticacaoResposta getAutenticacaoResposta() {
		return autenticacaoResposta;
	}

	public void setAutenticacaoResposta(AutenticacaoResposta autenticacaoResposta) {
		this.autenticacaoResposta = autenticacaoResposta;
	}

	public AutorizacaoServicoResposta getAutorizacaoServicoResposta() {
		return autorizacaoServicoResposta;
	}

	public void setAutorizacaoServicoResposta(AutorizacaoServicoResposta autorizacaoServicoResposta) {
		this.autorizacaoServicoResposta = autorizacaoServicoResposta;
	}

	@Override
	public String toString() {

		return super.toString();
	}

}
