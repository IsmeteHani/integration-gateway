package se.moln.integrationgateway.openapi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MergedOpenApiController {

    private final OpenApiMergeService service;

    public MergedOpenApiController(OpenApiMergeService service) {
        this.service = service;
    }

    @GetMapping(value = "/merged.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> merged() {
        return ResponseEntity.ok(service.merged());
    }
}
