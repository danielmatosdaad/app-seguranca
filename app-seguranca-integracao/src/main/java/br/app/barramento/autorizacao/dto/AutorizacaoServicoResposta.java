package br.app.barramento.autorizacao.dto;

import java.io.Serializable;
import java.util.List;

public class AutorizacaoServicoResposta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> urlsAutorizadas;

	public AutorizacaoServicoResposta() {
	}


	public List<String> getUrlsAutorizadas() {
		return urlsAutorizadas;
	}

	public void setUrlsAutorizadas(List<String> urlsAutorizadas) {
		this.urlsAutorizadas = urlsAutorizadas;
	}

}
