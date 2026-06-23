package com.M3Tours.empresas.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.M3Tours.empresas.DTO.LoginJWTDTO;
import com.M3Tours.empresas.DTO.ResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthService {
    private String secretKey = "mi_clave_secreta";

    public ResponseDTO validar(LoginJWTDTO requesJwtDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
            // 1. Simulación de validación de credenciales
        if ("admin".equals(requesJwtDTO.getUsername()) && "1234".equals(requesJwtDTO.getPassword())) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                // 2. Tiempo de expiración: 15 minutos
                long expTime = System.currentTimeMillis() + (15 * 60 * 1000);
                // 3. Crear el JWT
                String token = JWT.create()
                .withSubject(requesJwtDTO.getUsername())
                .withExpiresAt(new java.util.Date(expTime))
                .withClaim("roles",
                List.of("ROLE_ADMIN", "ROLE_PACIENTE"))
                .sign(algorithm);
                
                responseDTO.setRespuestaInteger(0);
                responseDTO.setRespuestaText(token);
                
                return responseDTO;    
            } catch (Exception e) {
                responseDTO.setRespuestaInteger(1);
                responseDTO.setRespuestaText("error al generar token");
                return responseDTO;
            }
        } else {
            responseDTO.setRespuestaInteger(2);
            responseDTO.setRespuestaText("credenciales inválidas");
            return responseDTO;
        }
    }   
}
