package br.app.barramento.autorizacao.dto;

import java.io.Serializable;

public class AutorizacaoServicoEnvio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idPerfil;

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

}
