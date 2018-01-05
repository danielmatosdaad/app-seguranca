package br.app.barramento.autenticacao.imp;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Named;

import br.app.barramento.autenticacao.dao.AutenticacaoFacede;
import br.app.barramento.autenticacao.dao.model.Autenticacao;
import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoResposta;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoLocal;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoRemote;
import br.app.barramento.controlesessao.api.ControleSessaoDelegate;
import br.app.barramento.controlesessao.dto.SessaoEnvioDTO;
import br.app.barramento.controlesessao.dto.SessaoRespostaDTO;
import br.app.barramento.controlesessao.dto.TipoSessao;
import br.app.barramento.controlesessao.interfaces.IControleSessao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.barramento.seguranca.dao.ParametroSegurancaFacede;

@Stateless
@Named
@Remote({ IAutenticacaoRemote.class })
@Local({ IAutenticacaoLocal.class })
public class AutenticacaoServiceImp implements IAutenticacaoRemote, IAutenticacaoLocal {

	@EJB
	private AutenticacaoFacede autenticacaoFacede;

	@EJB
	private ParametroSegurancaFacede parametroSegurancaFacede;

	@Override
	public AutenticacaoResposta autenticar(AutenticacaoEnvio autenticacaoEnvio)
			throws InfraEstruturaException, NegocioException {

		if (autenticacaoEnvio == null) {
			throw new NegocioException("Dados obrigatorio nao informado", new RuntimeException());
		}

		Autenticacao autenticacao = autenticacaoFacede
				.buscarAutenticacao(autenticacaoEnvio.getNomeIdentificadorAutenticacao(), autenticacaoEnvio.getSenha());
		if (autenticacao != null) {
			if (!autenticacao.isAtivo()) {
				throw new NegocioException("Nao esta ativo" + autenticacaoEnvio.getNomeIdentificadorAutenticacao()  , new RuntimeException());
			}
			if (!autenticacao.isLiberado()) {
				throw new NegocioException("Nao esta liberado" + autenticacaoEnvio.getNomeIdentificadorAutenticacao()  , new RuntimeException());
			}
		}
		ControleSessaoDelegate delegate = ControleSessaoDelegate.getInstancia();
		IControleSessao servico = delegate.getServico();

		SessaoEnvioDTO envioSessao = criarEnvioSessao(autenticacaoEnvio);

		SessaoRespostaDTO sessaoDTO = servico.abrir(envioSessao);

		AutenticacaoResposta autenticacaoResposta = new AutenticacaoResposta(
				autenticacaoEnvio.getNomeIdentificadorAutenticacao(), new Date(), sessaoDTO);

		
		
		return autenticacaoResposta;
	}

	private SessaoEnvioDTO criarEnvioSessao(AutenticacaoEnvio autenticacaoEnvio) {
		SessaoEnvioDTO envioSessao = new SessaoEnvioDTO();
		envioSessao.setNomeIdentificadorAutenticacao(autenticacaoEnvio.getNomeIdentificadorAutenticacao());
		envioSessao.setSenha(autenticacaoEnvio.getSenha());
		envioSessao.setBrownser(autenticacaoEnvio.getBrownser());
		envioSessao.setIp(autenticacaoEnvio.getIp());
		envioSessao.setPorta(autenticacaoEnvio.getPorta());
		envioSessao.setIdentificadorDispotivo(autenticacaoEnvio.getIdentificadorDispotivo());
		envioSessao.setDatahora(autenticacaoEnvio.getDatahora());

		if (autenticacaoEnvio.getTipoAutenticacao() == null) {
			throw new RuntimeException("Tipo autenticacao invalida");
		}

		switch (autenticacaoEnvio.getTipoAutenticacao()) {
		case APLICATIVO:
			envioSessao.setTipoSessao(TipoSessao.APLICATIVO);
			break;
		case USUARIO:
			envioSessao.setTipoSessao(TipoSessao.USUARIO);
			break;
		default:
			throw new RuntimeException("Tipo autenticacao invalida");
		}

		return envioSessao;
	}

}
