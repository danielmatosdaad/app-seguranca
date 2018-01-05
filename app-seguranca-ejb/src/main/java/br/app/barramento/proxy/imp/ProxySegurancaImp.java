package br.app.barramento.proxy.imp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;

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
import br.app.crypt.algorithm.AsymmetricAlgorithm;
import br.app.crypt.algorithm.CryptFactory;
import br.app.crypt.algorithm.asymmetric.AsymmetricCrypter;
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
	
	private KeyPair keys;

	@Override
	public String autenticacaoAutorizacao(AutenticacaoEnvio autenticacaoEnvio)
			throws NegocioException, InfraEstruturaException {

		if (autenticacaoEnvio == null) {
			throw new NegocioException("Dados obrigatorio nao informado", new RuntimeException());
		}

		this.autenticacaoResposta = this.autenticacao.autenticar(autenticacaoEnvio);
		this.autenticacaoHash = new AutenticacaoHash(autenticacaoEnvio.getNomeIdentificadorAutenticacao(),
				autenticacaoEnvio.getIp(), autenticacaoEnvio.getPorta(), autenticacaoEnvio.getIdentificadorDispotivo());

		gerarParChavesParaCriptografia();
		return autenticacaoHash.getTokenAutenticacao();

	}

	private void gerarParChavesParaCriptografia() throws InfraEstruturaException {
		try {
			AsymmetricCrypter asymmetricCrypter =CryptFactory.asymmetric().getCryptografy(AsymmetricAlgorithm.RSA_1024bits);
			asymmetricCrypter.generateKeys();
			this.keys = asymmetricCrypter.getKeys();
			
//			EncryptSet es = rsa.encrypt("input");
//			String encrypted = es.getContents();
//			String encryptedKey = es.getEncryptedKey();
			
//
////Obtenho a fábrica de criptografia assimétrica
//AsymmetricCryptFactory factory = AsymmetricCryptFactory.getInstance();
// 
////Escolho o algoritmo RSA de 1024bits passando a chave pública obtida na criptografia
//AsymmetricCrypter rsap = factory.getCryptografy(AsymmetricAlgorithm.RSA_1024bits,pubK);
// 
////Descriptografo o conteúdo criptografado, utilizando a chave criptografada criada junto ao conteudo criptografado. Pronto!
//String decryptedAgain = rsap.decrypt(encripted, encryptedKey);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw new InfraEstruturaException("Erro ao criar criptografia", new RuntimeException());
		} catch (InvalidKeyException e) {
			throw new InfraEstruturaException("Erro ao criar criptografia", new RuntimeException());
		} catch (InvalidKeySpecException e) {
			throw new InfraEstruturaException("Erro ao criar criptografia", new RuntimeException());
		} catch (ClassNotFoundException e) {
			throw new InfraEstruturaException("Erro ao criar criptografia", new RuntimeException());
		} catch (IOException e) {
			throw new InfraEstruturaException("Erro ao criar criptografia", new RuntimeException());
		}
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
