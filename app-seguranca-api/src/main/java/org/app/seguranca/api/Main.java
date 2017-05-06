
package org.app.seguranca.api;

import java.util.Date;

import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;

public class Main {

	public static void main(String args[]) throws InfraEstruturaException, NegocioException, ClassNotFoundException {

		AutenticacaoEnvio envio = new AutenticacaoEnvio();
		envio.setNomeIdentificadorAutenticacao("daniel.matos");
		envio.setSenha("123456");
		envio.setBrownser("safari");
		envio.setIpporta("127.0.0.1:8080");
		envio.setDatahora(new Date());
		envio.setIdentificadorDispotivo("macos");
		ProxySegurancaDelegate.getInstancia().autenticacaoAutorizacao(envio);
	}
}
