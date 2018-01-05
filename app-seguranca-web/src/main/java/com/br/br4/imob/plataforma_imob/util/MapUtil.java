package com.br.br4.imob.plataforma_imob.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

public final class MapUtil {
	
	private MapUtil(){}

	static public Map<String, String> convertMultiToRegularMap(MultivaluedMap<String, String> m) {
	    Map<String, String> map = new HashMap<String, String>();
	    if (m == null) {
	        return map;
	    }
	    for (Entry<String, List<String>> entry : m.entrySet()) {
	        StringBuilder sb = new StringBuilder();
	        for (String s : entry.getValue()) {
	            if (sb.length() > 0) {
	                sb.append(',');
	            }
	            sb.append(s);
	        }
	        map.put(entry.getKey(), sb.toString());
	    }
	    return map;
	}
	
}
