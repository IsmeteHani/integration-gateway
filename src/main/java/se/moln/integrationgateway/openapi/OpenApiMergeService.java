package se.moln.integrationgateway.openapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

@Service
public class OpenApiMergeService {

    private final RestClient http;
    private final ObjectMapper mapper = new ObjectMapper();
    private final int serverPort;

    public OpenApiMergeService(RestClient http, @Value("${server.port:8085}") int serverPort) {
        this.http = http;
        this.serverPort = serverPort;
    }

    public Map<String, Object> merged() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("openapi", "3.0.1");
        out.put("info", Map.of("title","All Services (Merged)","version","v1",
                "description","Integration Gateway + Product + Order + User (merged)"));
        out.put("paths", new LinkedHashMap<>());
        out.put("components", new LinkedHashMap<>());
        out.put("tags", new ArrayList<Map<String, Object>>());

        List<Src> sources = List.of(
                new Src("gateway", "http://localhost:" + serverPort + "/api-docs"),
                new Src("product", "http://localhost:" + serverPort + "/openapi/product"),
                new Src("order",   "http://localhost:" + serverPort + "/openapi/order"),
                new Src("user",    "http://localhost:" + serverPort + "/openapi/user")
        );

        for (Src s : sources) {
            Map<String, Object> in = fetchSafe(s.url(), s.name());
            if (!in.isEmpty()) mergeInto(out, in, s.name());
        }
        return out;
    }

    private record Src(String name, String url) {}

    private Map<String, Object> fetchSafe(String url, String name) {
        try {
            String json = http.get().uri(url).retrieve().body(String.class);
            Map<String, Object> map = mapper.readValue(json, new TypeReference<>() {});
            if (!map.containsKey("tags")) map.put("tags", List.of(Map.of("name", name)));
            return map;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    @SuppressWarnings("unchecked")
    private void mergeInto(Map<String, Object> out, Map<String, Object> in, String sourceName) {
        List<Map<String, Object>> outTags = (List<Map<String, Object>>) out.get("tags");
        List<Map<String, Object>> inTags  = (List<Map<String, Object>>) in.getOrDefault("tags", List.of());
        Set<String> seen = new HashSet<>();
        outTags.forEach(t -> seen.add(String.valueOf(t.get("name"))));
        for (Map<String, Object> t : inTags) {
            String name = String.valueOf(t.get("name"));
            if (seen.add(name)) outTags.add(t);
        }

        Map<String, Object> outPaths = (Map<String, Object>) out.get("paths");
        Map<String, Object> inPaths  = (Map<String, Object>) in.getOrDefault("paths", Map.of());

        for (var e : inPaths.entrySet()) {
            String originalPath = e.getKey();
            String newPath = switch (sourceName) {
                case "product" -> "/proxy/product" + originalPath;
                case "order"   -> "/proxy/order"   + originalPath;
                case "user"    -> "/proxy/user"    + originalPath;
                default        -> originalPath;
            };
            if (outPaths.containsKey(newPath)) newPath = newPath + "#dup#" + sourceName;
            outPaths.put(newPath, e.getValue());
        }

        Map<String, Object> outComp = (Map<String, Object>) out.get("components");
        Map<String, Object> inComp  = (Map<String, Object>) in.getOrDefault("components", Map.of());
        for (String section : List.of("schemas","parameters","responses","requestBodies",
                "headers","securitySchemes","examples")) {
            Map<String, Object> outSec = (Map<String, Object>) outComp.computeIfAbsent(section, k -> new LinkedHashMap<>());
            Map<String, Object> inSec  = (Map<String, Object>) inComp.get(section);
            if (inSec == null) continue;
            for (var ent : inSec.entrySet()) {
                String key = ent.getKey();
                outSec.put(outSec.containsKey(key) ? key + "_svc_" + sourceName : key, ent.getValue());
            }
        }
    }
}
