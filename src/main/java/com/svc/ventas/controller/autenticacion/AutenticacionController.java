package com.svc.ventas.controller.autenticacion;

import com.svc.ventas.message.request.AutenticacionRequest;
import com.svc.ventas.message.response.AutenticacionResponse;
import com.svc.ventas.service.IAutenticacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacion/")
@RequiredArgsConstructor
public class AutenticacionController {

    private final IAutenticacionService autenticacionService;

    @PostMapping("/signin")
    public ResponseEntity<AutenticacionResponse> signIn(
            @RequestBody AutenticacionRequest autenticacionRequest){
        return ResponseEntity.ok(autenticacionService.signIn(autenticacionRequest));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AutenticacionResponse> refreshToken(
            @RequestParam String refreshToken) throws IllegalAccessException {
        return ResponseEntity.ok(autenticacionService.getTokenByRefreshToken(refreshToken));
    }
}
