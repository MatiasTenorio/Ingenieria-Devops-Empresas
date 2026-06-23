package com.M3Tours.empresas.Controller;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private EmpresaService service;

    @PostMapping("/agregar-empresa")
    public ResponseEntity<String> save(@RequestBody EmpresaDTO empresa) {
        service.save(empresa);
        return ResponseEntity.ok("Empresa añadida con exito.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (service.delete(id)){
            return ResponseEntity.ok("Empresa eliminada con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con ID " + id + " No encontrada.");
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Empresa> responseService = service.findById(id);
        if (responseService.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con ID '" + id + "'' No encontrada.");
        }
        return ResponseEntity.ok(responseService.get());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        Optional<Empresa> responseService = service.findByNombre(nombre);
        if (responseService.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con nombre '" + nombre + "'' No encontrada.");
        }
        return ResponseEntity.ok(responseService.get());
    }

    @GetMapping("/rut/{rutEmpresa}")
    public ResponseEntity<?> getByRut(@PathVariable String rutEmpresa) {
        Optional<Empresa> responseService = service.findByRut(rutEmpresa);
        if (responseService.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa con RUT '" + rutEmpresa + "'' No encontrada.");
        }
        return ResponseEntity.ok(responseService.get());
    }
}