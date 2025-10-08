package com.gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(value = "/{service}")
    public ResponseEntity<String> genericServiceFallback(@PathVariable String service) {
        String body = String.format("El servicio '%s' no está disponible en este momento. Por favor, inténtalo más tarde.", service);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    @GetMapping("/{service}")
    public ResponseEntity<String> specificServiceFallback(@PathVariable String service) {
        return genericServiceFallback(service);
    }
}