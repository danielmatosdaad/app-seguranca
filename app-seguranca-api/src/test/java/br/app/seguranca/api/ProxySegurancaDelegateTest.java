package br.app.seguranca.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.app.seguranca.api.ProxySegurancaDelegate;
import br.app.barramento.autenticacao.dto.AutenticacaoEnvio;
import br.app.barramento.autenticacao.dto.TipoAutenticacao;
import br.app.barramento.integracao.dto.EnvioDTO;
import br.app.barramento.integracao.dto.Mensagem;
import br.app.barramento.integracao.dto.RespostaDTO;
import br.app.barramento.integracao.dto.TipoAcao;
import br.app.barramento.integracao.exception.InfraEstruturaException;
import br.app.barramento.integracao.exception.NegocioException;
import br.app.repositorio.servico.integracao.AcaoServicoDTO;
import br.app.repositorio.servico.integracao.CatalogoServicoDTO;
import br.app.repositorio.servico.integracao.InformacaoServicoDTO;
import br.app.repositorio.servico.integracao.RepositorioServicoDTO;
import br.app.repositorio.servico.integracao.ServicoDTO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ProxySegurancaDelegateTest extends TestCase {
	public ProxySegurancaDelegateTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ProxySegurancaDelegateTest.class);
	}

	public void testAutenticacaoAplicativo() {
		// AutenticacaoEnvio envio = new AutenticacaoEnvio();
		// envio.setNomeIdentificadorAutenticacao("daniel.matos");
		// envio.setSenha("123456");
		// envio.setBrownser("safari");
		// envio.setIpporta("127.0.0.1:8080");
		// envio.setDatahora(new Date());
		// envio.setIdentificadorDispotivo("macos");
		// ProxySegurancaDelegate.getInstancia().autenticacaoAutorizacao(envio);
	}

	public void testGravarRepositorio() {
		assertTrue(true);

		Date dataHoje = new Date();
		AutenticacaoEnvio autenticacaoEnvio = new AutenticacaoEnvio(dataHoje.getTime(), "daniel.matos", "adm",
				"127.0.0.1", "8080", "hashDispositivo", "", dataHoje, TipoAutenticacao.APLICATIVO);
		try {

			ProxySegurancaDelegate proxyDelegate = ProxySegurancaDelegate.getInstancia();
			String tokenAutenticacao = proxyDelegate.autenticacaoAutorizacao(autenticacaoEnvio);

			AcaoServicoDTO acao = new AcaoServicoDTO();
			acao.setNome("REGISTRAR_MONITORACAO");
			acao.setDescricao("REGISTRAR MONITORACAO");

			ServicoDTO servicoDTO = new ServicoDTO();
			servicoDTO.setAcaoServico(acao);
			servicoDTO.setAtivo(true);

			InformacaoServicoDTO informacaoServico = new InformacaoServicoDTO();
			informacaoServico.setAtivo(true);
			informacaoServico.setDelegate("br.app.monitoracao.api.MonitoracaoDelegate");
			informacaoServico.setEnvio("br.app.monitoracao.servico.integracao.MonitoracaoDTO");
			informacaoServico.setReposta("void");
			informacaoServico.setInterfaces("br.app.monitoracao.servico.integracao.IServicoMonitoracao");
			informacaoServico.setTokenAutorizacao("tokenAutorizacaoMonitoracao");

			servicoDTO.setInformacaoServico(informacaoServico);
			List<ServicoDTO> servicos = new ArrayList<ServicoDTO>();
			servicos.add(servicoDTO);

			List<CatalogoServicoDTO> catalogos = new ArrayList<CatalogoServicoDTO>();
			CatalogoServicoDTO catalogo = new CatalogoServicoDTO();
			catalogo.setAtivo(true);
			catalogo.setNome("RPC_MNT");
			catalogo.setNomeArtefatoId("app-monitoracao-servico-ejb");

			catalogo.setServicos(servicos);
			catalogos.add(catalogo);
			servicoDTO.setCatalogo(catalogo);
			servicoDTO.setInformacaoServico(informacaoServico);

			RepositorioServicoDTO repositorioServicoDTO = new RepositorioServicoDTO();
			repositorioServicoDTO.setNome("RESPOSITORIO_MONITORACAO_SERVICO_T");
			repositorioServicoDTO.setNomeArtefatoId("app-monitoracao-servico-ear");
			repositorioServicoDTO.setLoginServidor("admin");
			repositorioServicoDTO.setSenhaServidor("Daad161931[");
			repositorioServicoDTO.setIpServidor("127.0.0.1");
			repositorioServicoDTO.setPortaServidor("8080");

			repositorioServicoDTO.setCatalogo(catalogos);

			catalogo.setServicos(servicos);
			catalogo.setRepositorio(repositorioServicoDTO);

			System.out.println("Token de autenticacao:  " + tokenAutenticacao);
			String tokenAutorizacao = "tokenrespositorio";
			System.out.println("Token de autorizacao:  " + tokenAutorizacao);
			EnvioDTO envioDTO = new EnvioDTO(tokenAutenticacao, tokenAutorizacao, "daniel.matos", "127.0.0.1", "8080",
					"hashDispositivo", "RESPOSITORIO_CATALOGO_SERVICO", "RPO_SVC", TipoAcao.SALVAR.getValue(),
					repositorioServicoDTO, null);
			RespostaDTO respostaDTO = proxyDelegate.executar(TipoAcao.SALVAR, envioDTO);
			assertNotNull(respostaDTO);
			assertTrue(respostaDTO.getMensagem().equals(Mensagem.SUCESSO));

		} catch (NegocioException | InfraEstruturaException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
