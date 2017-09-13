package br.app.barramento.autenticacao.dto;

import java.io.Serializable;
import java.util.Date;

import br.app.barramento.controlesessao.dto.SessaoRespostaDTO;

public class AutenticacaoResposta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dataHoraAutenticacao;
	private SessaoRespostaDTO sessaoAutenticada;

	public AutenticacaoResposta() {

	}

	public AutenticacaoResposta(String nomeAutenticacao, Date dataHoraAutenticacao,
			SessaoRespostaDTO sessaoAutenticada) {
		super();
		this.dataHoraAutenticacao = dataHoraAutenticacao;
		this.sessaoAutenticada = sessaoAutenticada;

	}

	public Date getDataHoraAutenticacao() {
		return dataHoraAutenticacao;
	}

	public SessaoRespostaDTO getSessaoAutenticada() {
		return sessaoAutenticada;
	}

}
