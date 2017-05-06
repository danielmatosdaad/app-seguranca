package br.app.barramento.autenticacao.dao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.app.barramento.integracao.dao.interfaces.Entidade;


@Entity
@XmlRootElement
@Table(name = "autenticacao")
public class Autenticacao implements Entidade, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeAutenticacao;
	private String senha;
	private String codigoQrCode;
	private boolean ativo;
	private boolean liberado;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAutenticacao() {
		return nomeAutenticacao;
	}

	public void setNomeAutenticacao(String nomeAutenticacao) {
		this.nomeAutenticacao = nomeAutenticacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCodigoQrCode() {
		return codigoQrCode;
	}

	public void setCodigoQrCode(String codigoQrCode) {
		this.codigoQrCode = codigoQrCode;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}

}
