package com.M3Tours.empresas.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    public String mensaje;
    public String detalle;
    public int status;
    public LocalDateTime timeStamp;
}