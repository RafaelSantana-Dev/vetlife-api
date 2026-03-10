package com.vetlife.api.modules.system;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthController {
    @GetMapping("/health")
    public String check() { return "VetLife API - Operacional 🟢"; }
}