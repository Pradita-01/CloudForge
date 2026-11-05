
package edu.cia2.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @Value("${app.version:local}")
    private String appVersion;

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "application", "CIA 2 Java CI/CD Demo",
            "status", "running",
            "version", appVersion,
            "deploymentMessage",
            "Version 2 - Automatically deployed by Azure Pipelines!"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
