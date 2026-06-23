package com.M3Tours.empresas.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nombre_empresa", nullable=false, unique=true)
    private String nombreEmpresa;

    @Column(name="rut_empresa", nullable=false, length=10, unique=true)
    private String rutEmpresa;
    
    @Column(name="numero_empresa", nullable=false, length=15)
    private String numeroEmpresa;

    @Column(name="razon_social", nullable=false)
    private String razonSocial;
}
