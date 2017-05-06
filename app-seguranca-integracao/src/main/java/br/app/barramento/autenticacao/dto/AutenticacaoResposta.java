package br.app.barramento.autenticacao.dto;

import java.io.Serializable;
import java.util.Date;

import br.app.barramento.controlesessao.dto.SessaoRespostaDTO;


public class AutenticacaoResposta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomeAutenticacao;
	private Date dataHoraAutenticacao;
	private String tokenAutenticacao;
	private SessaoRespostaDTO sessaoAutenticada;

	public AutenticacaoResposta() {

	}

	public AutenticacaoResposta(String nomeAutenticacao, Date dataHoraAutenticacao, String tokenAutenticacao,
			SessaoRespostaDTO sessaoAutenticada) {
		super();
		this.nomeAutenticacao = nomeAutenticacao;
		this.dataHoraAutenticacao = dataHoraAutenticacao;
		this.tokenAutenticacao = tokenAutenticacao;
		this.tokenAutenticacao = tokenAutenticacao;
		this.sessaoAutenticada = sessaoAutenticada;
	}

	public String getNomeAutenticacao() {
		return nomeAutenticacao;
	}

	public Date getDataHoraAutenticacao() {
		return dataHoraAutenticacao;
	}

	public String getTokenAutenticacao() {
		return tokenAutenticacao;
	}

	public SessaoRespostaDTO getSessaoAutenticada() {
		return sessaoAutenticada;
	}

}
