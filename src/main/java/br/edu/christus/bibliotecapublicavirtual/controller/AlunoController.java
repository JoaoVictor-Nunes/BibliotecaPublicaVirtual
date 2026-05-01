package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import br.edu.christus.bibliotecapublicavirtual.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aluno")
public class AlunoController {
    @Autowired
    private AlunoService service;

    @PostMapping
    public Aluno create(@RequestBody Aluno aluno) {
        return service.save(aluno);
    }

    @PutMapping
    public Aluno update(@RequestBody Aluno aluno) {
        return service.save(aluno);
    }

    @GetMapping
    public List<Aluno> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Aluno findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}
