package com.pennhacks.ecolens.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class HomeController {

    @GetMapping("/index")
    public ResponseEntity<byte[]> getIndexPage() {
        return getStaticResource("static/index.html");
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> getIntroPage() {
        return getStaticResource("static/intro.html");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<byte[]> getDashboardPage() {
        return getStaticResource("static/dashboard.html");
    }

    private ResponseEntity<byte[]> getStaticResource(String resourcePath) {
        try {
            // Load the resource from the classpath
            Resource resource = new ClassPathResource(resourcePath);

            if (resource.exists()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    byte[] bytes = inputStream.readAllBytes();

                    // Serve the HTML file with the appropriate content type
                    return ResponseEntity.ok()
                            .contentType(MediaType.TEXT_HTML)
                            .body(bytes);
                }
            } else {
                // Handle the case where the HTML file is not found
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Handle an IO exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
