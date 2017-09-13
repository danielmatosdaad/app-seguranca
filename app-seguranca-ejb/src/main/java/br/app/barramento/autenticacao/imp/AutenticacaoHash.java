package br.app.barramento.autenticacao.imp;

import br.app.barramento.integracao.exception.NegocioException;
import br.app.hash.md5.HashSH2;

public class AutenticacaoHash {

	private final String tokenAutenticacao;

	public AutenticacaoHash(final String identificador, final String ip, final String porta,
			final String hashDispositivo) {

		tokenAutenticacao = geraHash(identificador, ip, porta, hashDispositivo);
	}

	public void validaHash(String identificador, String ip, String porta, String hashDispositivo) throws NegocioException {
		String hash = geraHash(identificador, ip, porta, hashDispositivo);
		if (!tokenAutenticacao.equals(hash)) {
			throw new NegocioException(-1, "Token de autenticao invalido");
		}
	}

	private static String geraHash(final String identificador, final String ip, final String porta,
			final String hashDispositivo) {
		String dados = identificador.concat(ip).concat(porta).concat(hashDispositivo).trim();
		String hash = HashSH2.gerar(dados);
		return hash;
	}

	public String getTokenAutenticacao() {
		return tokenAutenticacao;
	}

}