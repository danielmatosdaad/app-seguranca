package com.br4dev.imob.rest.interceptor;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

@Provider
public class SegurancaInterceptor implements javax.ws.rs.container.ContainerRequestFilter {

	@Context
	private HttpServletRequest httpServletRequest;

	@Context
	private ResourceInfo resourceInfo;

	boolean isJson(ContainerRequestContext request) {

		if (request != null && request.getMediaType() != null) {
			return request.getMediaType().toString().contains("application/json");
		}
		return false;

	}

	@Override
	public void filter(ContainerRequestContext requestContex) throws IOException {
		System.out.println(SegurancaInterceptor.class.getName());

		String remoteIpAddress = null;
		String userAgent = null;
		ReadableUserAgent agent = null;
		if (httpServletRequest != null) {
			ClienteHttpUtil.print(ClienteHttpUtil.getRequestHeadersInMap(httpServletRequest));
			remoteIpAddress = ClientIpAddress.getFrom(httpServletRequest, true);
			if (isJson(requestContex)) {
				userAgent = httpServletRequest.getHeader("User-Agent");

				if (userAgent != null) {
					UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
					agent = parser.parse(userAgent);
				}
				try {
					String json = tratarRequisicaoJson(requestContex.getEntityStream());
					if (json == null || json.isEmpty()) {
						return;
					}
					Gson gson = criarGson();
					JsonElement jsonElement = injetarInformacao(remoteIpAddress, agent, json, gson);
					System.out.println(gson.toJson(jsonElement));
					reconfiguraRequisicao(requestContex, gson, jsonElement);

				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}

	}

	private void reconfiguraRequisicao(ContainerRequestContext requestContex, Gson gson, JsonElement jsonElement) {
		InputStream in = IOUtils.toInputStream(gson.toJson(jsonElement));
		requestContex.setEntityStream(in);
	}

	private JsonElement injetarInformacao(String remoteIpAddress, ReadableUserAgent agent, String json, Gson gson) {
		JsonElement jsonElement = criarJsonElement(gson, json);
		if (remoteIpAddress != null) {
			jsonElement.getAsJsonObject().addProperty("ip", remoteIpAddress);
		}

		if (agent != null) {
			jsonElement.getAsJsonObject().addProperty("browser", agent.getFamily().getName());
			jsonElement.getAsJsonObject().addProperty("sistemaOperacional", agent.getOperatingSystem().getName());
			jsonElement.getAsJsonObject().addProperty("categoriaDispositivo", agent.getDeviceCategory().getName());
			jsonElement.getAsJsonObject().addProperty("agenteUsuario", agent.getType().getName());
			jsonElement.getAsJsonObject().addProperty("dispositivo", agent.getDeviceCategory().getCategory().getName());
		}

		return jsonElement;
	}

	private JsonElement criarJsonElement(Gson gson, String json) {
		JsonParser jsonParser = new JsonParser();
		jsonParser.parse(json).getAsJsonObject();
		JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
		return jsonElement;
	}

	private Gson criarGson() {
		Gson gson = new GsonBuilder().create();
		return gson;
	}

	private String tratarRequisicaoJson(InputStream entityStream) throws IOException {
		String json = IOUtils.toString(entityStream);
		json = json.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
		json = json.replaceAll("\\s+", "");
		json = json.trim();
		System.out.println("Requisicao:");
		System.out.println(json);
		return json;
	}

}
