package com.br4dev.imob.rest.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br4dev.imob.infraestrutura.dto.RespostaDTO;
import com.br4dev.imob.infraestrutura.execao.Mensagem;
import com.br4dev.imob.infraestrutura.execao.NegocioException;
import com.br4dev.imob.infraestrutura.servico.IServicoCreciCorretor;
import com.br4dev.imob.negocio.dto.CorretorDTO;

@Path("/creci")
@RequestScoped
public class CreciCorretorRest {

	@Inject
	private IServicoCreciCorretor creciServico;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validar")
	public RespostaDTO validarCorretorCreci(CorretorDTO corretor) {
		RespostaDTO respostaDTO = new RespostaDTO();
		try {
			boolean resposta = creciServico.validaPorInscricao(corretor.getCreci());

			if (resposta) {
				respostaDTO.setMensagem(Mensagem.SUCESSO);
				respostaDTO.setRespostaMensagem("valido");
			}
			return respostaDTO;
		} catch (NegocioException e) {
			respostaDTO.setMensagem(Mensagem.ERRO_NEGOCIO);
			respostaDTO.setRespostaMensagem(e.getMessage());
		} catch (Exception e) {
			respostaDTO.setMensagem(Mensagem.ERRO_INFRA);
			respostaDTO.setRespostaMensagem(e.getMessage());
		}

		return respostaDTO;

	}

	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {

		Map<String, String> responseObj = new HashMap<>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}
}
