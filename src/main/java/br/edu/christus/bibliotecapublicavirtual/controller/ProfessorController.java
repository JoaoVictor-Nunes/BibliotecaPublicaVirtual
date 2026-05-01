package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import br.edu.christus.bibliotecapublicavirtual.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @PostMapping
    public Professor create(@RequestBody Professor professor) {
        return service.save(professor);
    }

    @PutMapping
    public Professor update(@RequestBody Professor professor) {
        return service.save(professor);
    }

    @GetMapping
    public List<Professor> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Professor findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
