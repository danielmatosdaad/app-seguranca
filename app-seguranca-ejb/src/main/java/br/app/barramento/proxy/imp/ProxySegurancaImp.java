package br.app.barramento.proxy.imp;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.inject.Named;

import org.app.seguranca.integracao.IProxySegurancaConexaoLocal;
import org.app.seguranca.integracao.IProxySegurancaConexaoRemote;

import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoResposta;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoLocal;
import br.app.barramento.autorizacao.dto.AutorizacaoServicoEnvio;
import br.app.barramento.autorizacao.dto.AutorizacaoServicoResposta;
import br.app.barramento.autorizacao.interfaces.IAutorizacaoServiceLocal;
import br.app.barramento.controlesessao.interfaces.IConexao;
import br.app.barramento.controlesessao.interfaces.ISessao;
import br.app.barramento.integracao.dto.EnvioDTO;
import br.app.barramento.integracao.dto.RespostaDTO;
import br.app.barramento.integracao.dto.TipoAcao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.corporativo.integracao.api.IntegracaoDelegate;
import br.app.repositorio.servico.integracao.InformacaoServico;

@Stateful
@Named
@Remote({ IProxySegurancaConexaoRemote.class })
@Local({ IProxySegurancaConexaoLocal.class })
public class ProxySegurancaImp implements IProxySegurancaConexaoLocal, IProxySegurancaConexaoRemote {


	@EJB
	private IAutenticacaoLocal autenticacao;
	@EJB
	private IAutorizacaoServiceLocal autorizacao;

	private AutenticacaoResposta autenticacaoResposta;
	private AutorizacaoServicoResposta autorizacaoServicoResposta;

	@Override
	public void autenticacaoAutorizacao(AutenticacaoEnvio autenticacaoEnvio)
			throws NegocioException, InfraEstruturaException {

		if (autenticacaoEnvio == null) {
			throw new NegocioException("Dados obrigatorio nao informado", new RuntimeException());
		}

		this.autenticacaoResposta = this.autenticacao.autenticar(autenticacaoEnvio);

		AutorizacaoServicoEnvio autorizacaoEnvio = new AutorizacaoServicoEnvio();
		this.autorizacaoServicoResposta = autorizacao.autorizacao(autorizacaoEnvio);

	}

	public IConexao getConexao() throws InfraEstruturaException {
		if (this.autenticacaoResposta == null) {
			throw new InfraEstruturaException(-1, "Coxexao Encerrada");
		}
		return this.autenticacaoResposta.getSessaoAutenticada().getConexao();
	}

	public ISessao getInformacaoSessao() throws InfraEstruturaException {
		if (this.autenticacaoResposta == null) {
			throw new InfraEstruturaException(-1, "Sessao Encerrada");
		}
		return this.autenticacaoResposta.getSessaoAutenticada().getSessao();
	}

	public RespostaDTO executar(TipoAcao acao, EnvioDTO envio, String nomeRepositorio, String nomeCatalogo)
			throws NegocioException, InfraEstruturaException {

		try {

				System.out.println("Proxy Seguranca executando...");
				System.out.println("repositorio: " + nomeRepositorio + " catalogo: " + nomeCatalogo + " acao:" + acao.getValue()
						+ " requisicao: " + envio.getEnvio());
			InformacaoServico info = getInformacaoSessao().getRepositorioServico()
					.buscarInformacaoServico(nomeRepositorio, nomeCatalogo, acao.getValue(), envio.getEnvio());

			if (info == null) {
				throw new InfraEstruturaException("Servico nao encontrado");
			}
			envio.setDelegate(info.getDelegate());
			envio.setInterfaces(info.getInterfaces());
			envio.setReposta(info.getReposta());
			return IntegracaoDelegate.getInstancia().executar(acao, envio);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new InfraEstruturaException(e1);
		}
	}

}
