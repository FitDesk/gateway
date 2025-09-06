package com.gateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/billing")
    public String billingServiceFallback(){
        return "El servicio de facturación no está disponible en este momento. Por favor, inténtalo más tarde.";
    }
    @GetMapping("/chat")
    public String chatServiceFallback(){
        return "El servicio de chat no está disponible en este momento. Por favor, inténtalo más tarde.";
    }
    @GetMapping("/classes")
    public String classesServiceFallback(){
        return "El servicio de clases no está disponible en este momento. Por favor, inténtalo más tarde.";
    }
    @GetMapping("/members")
    public String membersServiceFallback(){
        return "El servicio de miembros no está disponible en este momento. Por favor, inténtalo más tarde.";
    }
    @GetMapping("/notifications")
    public String notificationsServiceFallback(){
        return "El servicio de notificaciones no está disponible en este momento. Por favor, inténtalo más tarde.";
    }
    @GetMapping("/security")
    public String securityServiceFallback(){
        return "El servicio de autenticacion no está disponible en este momento. Por favor, inténtalo más tarde.";
    }

}
