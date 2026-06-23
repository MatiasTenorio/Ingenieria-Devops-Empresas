package com.M3Tours.empresas.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M3Tours.empresas.DTO.EmpresaDTO;
import com.M3Tours.empresas.Model.Empresa;
import com.M3Tours.empresas.Repository.EmpresaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpresaService {
    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> findAll(){
        return repository.findAll();
    }

    public Optional<Empresa> findById(Integer id){
        return repository.findById(id);
    }

    public Optional<Empresa> findByNombre(String nombre){
        return repository.findByNombreEmpresa(nombre);
    }

    public Optional<Empresa> findByRut(String rutEmpresa){
        return repository.findByRutEmpresa(rutEmpresa);
    }

    public Empresa save(EmpresaDTO DTO){
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa(DTO.getNombreEmpresa());
        empresa.setRutEmpresa(DTO.getRutEmpresa());
        empresa.setNumeroEmpresa(DTO.getNumeroEmpresa());
        empresa.setRazonSocial(DTO.getRazonSocial());

        Empresa empresaSave = repository.save(empresa);
        return empresaSave;
    }
    
    public Boolean delete(Integer id){ 
        if (repository.findById(id).isEmpty()){
            return false;
        }
        repository.delete(repository.findById(id).get());
        return true;
    }
}
