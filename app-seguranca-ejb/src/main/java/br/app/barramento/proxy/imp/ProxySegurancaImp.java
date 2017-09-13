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
import br.app.barramento.autenticacao.imp.AutenticacaoHash;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoLocal;
import br.app.barramento.controlesessao.interfaces.IConexao;
import br.app.barramento.controlesessao.interfaces.ISessao;
import br.app.barramento.integracao.dto.EnvioDTO;
import br.app.barramento.integracao.dto.RespostaDTO;
import br.app.barramento.integracao.dto.TipoAcao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.repositorio.servico.integracao.CatalogoServico;
import br.app.repositorio.servico.integracao.IRepositorio;
import br.app.repositorio.servico.integracao.InformacaoServico;
import br.app.repositorio.servico.integracao.RepositorioServico;

@Stateful
@Named
@Remote({ IProxySegurancaConexaoRemote.class })
@Local({ IProxySegurancaConexaoLocal.class })
public class ProxySegurancaImp implements IProxySegurancaConexaoLocal, IProxySegurancaConexaoRemote {

	@EJB
	private IAutenticacaoLocal autenticacao;

	private AutenticacaoResposta autenticacaoResposta;

	private AutenticacaoHash autenticacaoHash;

	@Override
	public String autenticacaoAutorizacao(AutenticacaoEnvio autenticacaoEnvio)
			throws NegocioException, InfraEstruturaException {

		if (autenticacaoEnvio == null) {
			throw new NegocioException("Dados obrigatorio nao informado", new RuntimeException());
		}

		this.autenticacaoResposta = this.autenticacao.autenticar(autenticacaoEnvio);
		this.autenticacaoHash = new AutenticacaoHash(autenticacaoEnvio.getNomeIdentificadorAutenticacao(),
				autenticacaoEnvio.getIp(), autenticacaoEnvio.getPorta(), autenticacaoEnvio.getIdentificadorDispotivo());

		return autenticacaoHash.getTokenAutenticacao();

	}

	public IConexao getConexao() throws InfraEstruturaException, NegocioException {
		if (this.autenticacaoResposta == null) {
			throw new NegocioException(-1, "Sessao Encerrada");
		}
		return this.autenticacaoResposta.getSessaoAutenticada().getConexao();
	}

	public ISessao getInformacaoSessao() throws InfraEstruturaException, NegocioException {
		if (this.autenticacaoResposta == null) {
			throw new NegocioException(-1, "Sessao Encerrada");
		}
		return this.autenticacaoResposta.getSessaoAutenticada().getSessao();
	}

	public RespostaDTO executar(TipoAcao acao, EnvioDTO envio) throws NegocioException, InfraEstruturaException {

		validaEnvio(envio);
		try {

			System.out.println("Proxy Seguranca executando...");

			RepositorioServico repositorioServico = null;
			CatalogoServico catalogo = null;
			InformacaoServico informacaoServico = null;
			ISessao sessao = null;
			if (autenticacaoResposta == null || autenticacaoResposta.getSessaoAutenticada() == null) {

				throw new NegocioException("Sem sessao", new RuntimeException());
			}

			sessao = autenticacaoResposta.getSessaoAutenticada().getSessao();
			if (sessao != null) {

				IRepositorio repositorio = sessao.getRepositorioServico();
				if (repositorio == null) {
					throw new NegocioException("Repositorio nao encontrado: " + envio.getNomeRepositorio(),
							new RuntimeException());
				}

				if (repositorio != null) {
					repositorioServico = repositorio.buscar(envio.getNomeRepositorio());
					catalogo = repositorioServico.buscar(envio.getNomeCatalogo());
					informacaoServico = catalogo.buscarInformacaoServico(envio.getAcao(), envio.getEnvio(),
							envio.getTokenAutorizacao());

					envio.setDelegate(informacaoServico.getDelegate());
					envio.setInterfaces(informacaoServico.getInterfaces());
					envio.setReposta(informacaoServico.getReposta());
					return getConexao().executar(acao, envio, repositorioServico.getNomeArtefatoId(),
							catalogo.getNomeArtefatoId(), repositorioServico.getIpServidor(),
							repositorioServico.getPortaServidor(), repositorioServico.getLoginServidor(),
							repositorioServico.getSenhaServidor());
				}

			}

			throw new NegocioException("Nao foi possivel executar a transacao", new RuntimeException());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new InfraEstruturaException(e1);
		}
	}

	private void validaEnvio(EnvioDTO envio) throws NegocioException, InfraEstruturaException {
		if (envio == null) {
			throw new NegocioException(-1, "Requisicao invalido");
		}

		if (envio.getTokenAutenticacao() == null) {
			throw new NegocioException(-1, "Token invalido");
		}

		if (this.autenticacaoResposta == null) {
			throw new NegocioException(-1, "Sessao Encerrada");
		}

		this.autenticacaoHash.validaHash(envio.getNomeIdentificadorAutenticacao(), envio.getIp(), envio.getPorta(),
				envio.getIdentificadorDispotivo());

	}

}
