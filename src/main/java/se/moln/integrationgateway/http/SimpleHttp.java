package se.moln.integrationgateway.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SimpleHttp {

    private final RestClient client;

    public SimpleHttp(RestClient client) {
        this.client = client;
    }

    public ResponseEntity<String> get(String url) {
        return client.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
    }
}
