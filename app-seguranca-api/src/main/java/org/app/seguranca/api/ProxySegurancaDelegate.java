package org.app.seguranca.api;

import org.app.seguranca.integracao.IProxySegurancaConexao;

import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.integracao.dto.EnvioDTO;
import br.app.barramento.integracao.dto.LocalizadorServico;
import br.app.barramento.integracao.dto.Mensagem;
import br.app.barramento.integracao.dto.RespostaDTO;
import br.app.barramento.integracao.dto.TipoAcao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.smart.api.infra.AbstractDelegate;
import br.app.smart.api.infra.TipoLocalizador;

public class ProxySegurancaDelegate extends AbstractDelegate<IProxySegurancaConexao> {

	private IProxySegurancaConexao proxy;

	public ProxySegurancaDelegate(LocalizadorServico<IProxySegurancaConexao> localizaServico) {
		super(localizaServico);
	}

	public IProxySegurancaConexao getServico() {
		return getLocalizadorServico().getServico();
	}

	public static ProxySegurancaDelegate getInstancia() {

		LocalizadorServico<IProxySegurancaConexao> localizaServico = new LocalizarServicoProxySeguranca<IProxySegurancaConexao>(
				TipoLocalizador.REMOTO);

		ProxySegurancaDelegate delegate = new ProxySegurancaDelegate(localizaServico);

		return delegate;
	}

	public String autenticacaoAutorizacao(AutenticacaoEnvio autenticacaoEnvio)
			throws NegocioException, InfraEstruturaException {
		this.proxy = getServico();
		return proxy.autenticacaoAutorizacao(autenticacaoEnvio);
	}

	@Override
	public LocalizadorServico<IProxySegurancaConexao> getLocalizadorServico(TipoAcao acao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executarServico(TipoAcao acao, EnvioDTO envio, RespostaDTO resposta, IProxySegurancaConexao servico)
			throws NegocioException, InfraEstruturaException {
		resposta.setMensagem(Mensagem.ERRO);
		resposta.getMensagem().setErro("Funcionalidade nao implementada:" + acao.getValue());
	}

	public RespostaDTO executar(TipoAcao acao, EnvioDTO envio) throws NegocioException, InfraEstruturaException {
		if (this.proxy == null) {
			throw new NegocioException("nao foi aberto uma sessao segura", new RuntimeException());
		}
		return this.proxy.executar(acao, envio);
	}
}
