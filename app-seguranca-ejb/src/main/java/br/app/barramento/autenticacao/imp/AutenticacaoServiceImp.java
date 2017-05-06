package br.app.barramento.autenticacao.imp;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Named;

import br.app.barramento.autenticacao.dao.AutenticacaoFacede;
import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.autenticacao.dto.AutenticacaoResposta;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoLocal;
import br.app.barramento.autenticacao.interfaces.IAutenticacaoRemote;
import br.app.barramento.autorizacao.interfaces.IAutorizacaoServiceLocal;
import br.app.barramento.controlesessao.api.ControleSessaoDelegate;
import br.app.barramento.controlesessao.dto.SessaoEnvioDTO;
import br.app.barramento.controlesessao.dto.SessaoRespostaDTO;
import br.app.barramento.controlesessao.dto.TipoSessao;
import br.app.barramento.controlesessao.interfaces.IControleSessao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.barramento.seguranca.dao.ParametroSegurancaFacede;
import br.app.hash.md5.HashSH2;

@Stateless
@Named
@Remote({ IAutenticacaoRemote.class })
@Local({ IAutenticacaoLocal.class })
public class AutenticacaoServiceImp implements IAutenticacaoRemote, IAutenticacaoLocal {

	@EJB
	private AutenticacaoFacede autenticacaoFacede;

	@EJB
	private ParametroSegurancaFacede parametroSegurancaFacede;

	@EJB
	private IAutorizacaoServiceLocal autorizacaoService;

	@Override
	public AutenticacaoResposta autenticar(AutenticacaoEnvio autenticacaoEnvio)
			throws InfraEstruturaException, NegocioException {

		if (autenticacaoEnvio == null) {
			throw new NegocioException("Dados obrigatorio nao informado", new RuntimeException());
		}

		ControleSessaoDelegate delegate = ControleSessaoDelegate.getInstancia();
		IControleSessao servico = delegate.getServico();

		SessaoEnvioDTO envioSessao = criarEnvioSessao(autenticacaoEnvio);

		SessaoRespostaDTO resposta = servico.abrir(envioSessao);

		String dadosusuario = autenticacaoEnvio.getNomeIdentificadorAutenticacao().concat(autenticacaoEnvio.getSenha())
				.concat(autenticacaoEnvio.getIpporta()).concat(autenticacaoEnvio.getBrownser())
				.concat(autenticacaoEnvio.getIdentificadorDispotivo());

		String tokenAutenticacao = HashSH2.gerar(dadosusuario);

		AutenticacaoResposta autenticacaoResposta = new AutenticacaoResposta(
				autenticacaoEnvio.getNomeIdentificadorAutenticacao(), new Date(), tokenAutenticacao, resposta);

		return autenticacaoResposta;
	}

	private SessaoEnvioDTO criarEnvioSessao(AutenticacaoEnvio autenticacaoEnvio) {
		SessaoEnvioDTO envioSessao = new SessaoEnvioDTO();
		envioSessao.setNomeIdentificadorAutenticacao(autenticacaoEnvio.getNomeIdentificadorAutenticacao());
		envioSessao.setSenha(autenticacaoEnvio.getSenha());
		envioSessao.setBrownser(autenticacaoEnvio.getBrownser());
		envioSessao.setIpporta(autenticacaoEnvio.getIpporta());
		envioSessao.setIdentificadorDispotivo(autenticacaoEnvio.getIdentificadorDispotivo());
		envioSessao.setDatahora(autenticacaoEnvio.getDatahora());

		if(autenticacaoEnvio.getTipoAutenticacao()==null){
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
