package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

    @Value("${app.version:unknown}")
    private String appVersion;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Hello World - Spring Boot</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        max-width: 800px;
                        margin: 50px auto;
                        padding: 20px;
                    }
                    .info { background-color: #f0f0f0; padding: 15px; border-radius: 5px; }
                    h1 { color: #2c3e50; }
                </style>
            </head>
            <body>
                <h1>Hello World Application (Spring Boot)</h1>
                <div class="info">
                    <p><strong>Version:</strong> %s</p>
                    <p><strong>Framework:</strong> Spring Boot</p>
                    <p><strong>Language:</strong> Java</p>
                </div>
                <h2>Available Endpoints:</h2>
                <ul>
                    <li><a href="/health">/health</a> - Health check endpoint</li>
                    <li><a href="/ready">/ready</a> - Readiness check endpoint</li>
                </ul>
            </body>
            </html>
            """.formatted(appVersion);
        
        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("version", appVersion);
        response.put("framework", "Spring Boot");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ready")
    public ResponseEntity<Map<String, Object>> ready() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ready");
        response.put("version", appVersion);
        response.put("framework", "Spring Boot");
        return ResponseEntity.ok(response);
    }
}
