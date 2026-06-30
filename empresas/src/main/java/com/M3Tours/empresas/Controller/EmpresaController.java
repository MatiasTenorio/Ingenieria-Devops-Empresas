package com.M3Tours.empresas.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.M3Tours.empresas.DTO.EmpresaDTO;
import com.M3Tours.empresas.Model.Empresa;
import com.M3Tours.empresas.Service.EmpresaService;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService service;

    @PostMapping("/agregar-empresa")
    public ResponseEntity<String> save(@RequestBody EmpresaDTO empresa) {
        long startTime = System.currentTimeMillis();
        service.save(empresa);
        long duration = System.currentTimeMillis() - startTime;

        MDC.put("action", "CREAR_EMPRESA");
        MDC.put("nombre_empresa", empresa.getNombreEmpresa());
        MDC.put("duration_ms", String.valueOf(duration));
        MDC.put("http_path", "/api/v1/empresas/agregar-empresa");

        log.info("EMPRESA_EVENT - Empresa creada exitosamente.");
        MDC.clear();

        return ResponseEntity.ok("Empresa añadida con exito.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        MDC.put("action", "ELIMINAR_EMPRESA");
        MDC.put("empresa_id", String.valueOf(id));
        MDC.put("http_path", "/api/v1/empresas/" + id);

        if (service.delete(id)) {
            log.info("EMPRESA_EVENT - Empresa eliminada exitosamente.");
            MDC.clear();
            return ResponseEntity.ok("Empresa eliminada con exito");
        }

        log.warn("EMPRESA_EVENT - Intento de eliminar empresa inexistente.");
        MDC.clear();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con ID " + id + " No encontrada.");
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getAll() {
        long startTime = System.currentTimeMillis();
        List<Empresa> listaEmpresas = service.findAll();
        long duration = System.currentTimeMillis() - startTime;

        MDC.put("action", "LISTAR_EMPRESAS");
        MDC.put("total_empresas", String.valueOf(listaEmpresas.size()));
        MDC.put("duration_ms", String.valueOf(duration));
        MDC.put("http_path", "/api/v1/empresas");

        if (listaEmpresas.isEmpty()) {
            log.warn("EMPRESA_EVENT - Se consultaron las empresas pero no existen registros.");
        } else {
            log.info("EMPRESA_EVENT - Consulta de empresas exitosa.");
        }
        MDC.clear();

        return ResponseEntity.ok(listaEmpresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        MDC.put("action", "BUSCAR_EMPRESA_POR_ID");
        MDC.put("empresa_id", String.valueOf(id));
        MDC.put("http_path", "/api/v1/empresas/" + id);

        Optional<Empresa> responseService = service.findById(id);
        if (responseService.isEmpty()) {
            log.warn("EMPRESA_EVENT - Empresa no encontrada por ID.");
            MDC.clear();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con ID '" + id + "'' No encontrada.");
        }

        log.info("EMPRESA_EVENT - Empresa encontrada por ID.");
        MDC.clear();
        return ResponseEntity.ok(responseService.get());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        MDC.put("action", "BUSCAR_EMPRESA_POR_NOMBRE");
        MDC.put("nombre_empresa", nombre);
        MDC.put("http_path", "/api/v1/empresas/nombre/" + nombre);

        Optional<Empresa> responseService = service.findByNombre(nombre);
        if (responseService.isEmpty()) {
            log.warn("EMPRESA_EVENT - Empresa no encontrada por nombre.");
            MDC.clear();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con nombre '" + nombre + "'' No encontrada.");
        }

        log.info("EMPRESA_EVENT - Empresa encontrada por nombre.");
        MDC.clear();
        return ResponseEntity.ok(responseService.get());
    }

    @GetMapping("/rut/{rutEmpresa}")
    public ResponseEntity<?> getByRut(@PathVariable String rutEmpresa) {
        MDC.put("action", "BUSCAR_EMPRESA_POR_RUT");
        MDC.put("rut_empresa", rutEmpresa);
        MDC.put("http_path", "/api/v1/empresas/rut/" + rutEmpresa);

        Optional<Empresa> responseService = service.findByRut(rutEmpresa);
        if (responseService.isEmpty()) {
            log.warn("EMPRESA_EVENT - Empresa no encontrada por RUT.");
            MDC.clear();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con RUT '" + rutEmpresa + "'' No encontrada.");
        }

        log.info("EMPRESA_EVENT - Empresa encontrada por RUT.");
        MDC.clear();
        return ResponseEntity.ok(responseService.get());
    }
}