package br.app.barramento.autenticacao.dto;

import java.io.Serializable;
import java.util.Date;

public class AutenticacaoEnvio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idetificadorAutenticacao;
	private String nomeIdentificadorAutenticacao;
	private String senha;
	private String ip;
	private String porta;
	private String identificadorDispotivo;
	private String brownser;
	private Date datahora;
	private TipoAutenticacao tipoAutenticacao;

	public AutenticacaoEnvio() {
	}

	public AutenticacaoEnvio(Long idetificadorAutenticacao, String nomeIdentificadorAutenticacao, String senha,
			String ip, String porta, String identificadorDispotivo, String brownser, Date datahora,
			TipoAutenticacao tipoAutenticacao) {

		this.idetificadorAutenticacao = idetificadorAutenticacao;
		this.nomeIdentificadorAutenticacao = nomeIdentificadorAutenticacao;
		this.senha = senha;
		this.ip = ip;
		this.porta = porta;
		this.identificadorDispotivo = identificadorDispotivo;
		this.brownser = brownser;
		this.datahora = datahora;
		this.tipoAutenticacao = tipoAutenticacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdetificadorAutenticacao() {
		return idetificadorAutenticacao;
	}

	public void setIdetificadorAutenticacao(Long idetificadorAutenticacao) {
		this.idetificadorAutenticacao = idetificadorAutenticacao;
	}

	public String getNomeIdentificadorAutenticacao() {
		return nomeIdentificadorAutenticacao;
	}

	public void setNomeIdentificadorAutenticacao(String nomeIdentificadorAutenticacao) {
		this.nomeIdentificadorAutenticacao = nomeIdentificadorAutenticacao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getIdentificadorDispotivo() {
		return identificadorDispotivo;
	}

	public void setIdentificadorDispotivo(String identificadorDispotivo) {
		this.identificadorDispotivo = identificadorDispotivo;
	}

	public String getBrownser() {
		return brownser;
	}

	public void setBrownser(String brownser) {
		this.brownser = brownser;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public TipoAutenticacao getTipoAutenticacao() {
		return tipoAutenticacao;
	}

	public void setTipoAutenticacao(TipoAutenticacao tipoAutenticacao) {
		this.tipoAutenticacao = tipoAutenticacao;
	}

}
