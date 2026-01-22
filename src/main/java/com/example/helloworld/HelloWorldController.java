package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    
    @Value("${app.environment}")
    private String environment;
    
    @Value("${app.version}")
    private String version;

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String timestamp = Instant.now().toString();
        String hostname = getHostname();
        
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Hello World - %s</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .info { background: #f0f0f0; padding: 20px; border-radius: 5px; }
                    h1 { color: #333; }
                    .env { color: #0066cc; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Hello World Spring Boot!</h1>
                    <div class="info">
                        <p><strong>Environment:</strong> <span class="env">%s</span></p>
                        <p><strong>Version:</strong> %s</p>
                        <p><strong>Timestamp:</strong> %s</p>
                        <p><strong>Hostname:</strong> %s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(environment, environment, version, timestamp, hostname);
        
        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("environment", environment);
        response.put("version", version);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ready")
    public ResponseEntity<Map<String, Object>> ready() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ready");
        response.put("environment", environment);
        response.put("version", version);
        return ResponseEntity.ok(response);
    }
}
