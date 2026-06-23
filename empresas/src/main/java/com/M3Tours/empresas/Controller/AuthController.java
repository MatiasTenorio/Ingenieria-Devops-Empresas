package com.M3Tours.empresas.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.M3Tours.empresas.DTO.LoginJWTDTO;
import com.M3Tours.empresas.DTO.ResponseDTO;
import com.M3Tours.empresas.Service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
    @RequestBody LoginJWTDTO request) {
        ResponseDTO response = authService.validar(request);
        switch (response.getRespuestaInteger()) {
            case 0:
                return ResponseEntity.ok(Map.of("token", response.getRespuestaText()));
            case 1:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.getRespuestaText());
            case 2:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.getRespuestaText());
            default:
                break;
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error no capturado");
    }
}
