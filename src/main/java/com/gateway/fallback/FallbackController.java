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


    @GetMapping("/billing")
    public ResponseEntity<String> billingServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de facturación no está disponible en este momento. Por favor, inténtalo más tarde.");
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chatServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de chat no está disponible en este momento. Por favor, inténtalo más tarde.");
    }

    @GetMapping("/classes")
    public ResponseEntity<String> classesServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de clases no está disponible en este momento. Por favor, inténtalo más tarde.");
    }

    @GetMapping("/members")
    public ResponseEntity<String> membersServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de miembros no está disponible en este momento. Por favor, inténtalo más tarde.");
    }

    @GetMapping("/notifications")
    public ResponseEntity<String> notificationsServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de notificaciones no está disponible en este momento. Por favor, inténtalo más tarde.");
    }

    @GetMapping("/security")
    public ResponseEntity<String> securityServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de autenticacion no está disponible en este momento. Por favor, inténtalo más tarde.");
    }


}
