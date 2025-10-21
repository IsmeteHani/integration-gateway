package se.moln.integrationgateway.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final RestTemplate http;
    private final Map<String, String> targets;

    public ProxyController(
            RestTemplate http,
            @Value("${services.product.url}") String productBase,
            @Value("${services.order.url}") String orderBase,
            @Value("${services.user.url}") String userBase
    ) {
        this.http = http;
        this.targets = Map.of(
                "product", productBase,
                "order",   orderBase,
                "user",    userBase
        );
    }

    @RequestMapping(value = "/{target}/**")
    public ResponseEntity<byte[]> forward(
            @PathVariable String target,
            HttpMethod method,
            HttpServletRequest req,
            @RequestHeader HttpHeaders headers
    ) throws IOException {

        String base = targets.get(target.toLowerCase());
        if (base == null) {
            return ResponseEntity.badRequest().body(("Unknown target: " + target).getBytes());
        }

        String fullPath = req.getRequestURI();
        String stripPrefix = "/proxy/" + target;
        String tail = fullPath.startsWith(stripPrefix) ? fullPath.substring(stripPrefix.length()) : fullPath;
        String url = base + tail + (req.getQueryString() != null ? "?" + req.getQueryString() : "");

        HttpHeaders out = new HttpHeaders();
        out.putAll(headers);
        out.remove(HttpHeaders.HOST);
        out.remove(HttpHeaders.CONTENT_LENGTH);

        byte[] body = StreamUtils.copyToByteArray(req.getInputStream());
        HttpEntity<byte[]> entity = new HttpEntity<>(body, out);

        return http.exchange(URI.create(url), method, entity, byte[].class);
    }
}
