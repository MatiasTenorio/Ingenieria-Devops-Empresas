package com.M3Tours.empresas.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.M3Tours.empresas.Model.Empresa;



@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
    Optional<Empresa> findByNombreEmpresa(String nombreEmpresa);

    @Query(value="Select e from empresa e rut_empresa= :rutEmpresa", nativeQuery = true)
    Optional<Empresa> findByRutEmpresa(String rutEmpresa);
}
