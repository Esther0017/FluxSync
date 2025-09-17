package com.esther.fluxsync.controller;

import org.springframework.web.bind.annotation.*;

@RestController
class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
