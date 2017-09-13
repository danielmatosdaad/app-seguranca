package br.app.barramento.autenticacao.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import br.app.barramento.integracao.dao.interfaces.Entidade;

@Entity
@XmlRootElement
public class Autenticacao implements Entidade, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 20, message = "Tamanho maximo de caracteres sao 20")
	private String nomeAutenticacao;
	@NotNull
	@Size(min = 1, max = 100, message = "Tamanho maximo de caracteres sao 100")
	private String senhaAutenticacao;
	@NotNull
	private boolean ativo;
	@NotNull
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

	public String getSenhaAutenticacao() {
		return senhaAutenticacao;
	}

	public void setSenhaAutenticacao(String senhaAutenticacao) {
		this.senhaAutenticacao = senhaAutenticacao;
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
