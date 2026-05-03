package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.ProfessorDTO;
import br.edu.christus.bibliotecapublicavirtual.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @PostMapping
    public ProfessorDTO create(@RequestBody ProfessorDTO professorDTO) {
        return service.save(professorDTO);
    }

    @PutMapping
    public ProfessorDTO update(@RequestBody ProfessorDTO professorDTO) {
        return service.save(professorDTO);
    }

    @GetMapping
    public List<ProfessorDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProfessorDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
