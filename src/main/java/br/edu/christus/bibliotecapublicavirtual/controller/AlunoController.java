package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AlunoDTO;
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
    public AlunoDTO create(@RequestBody AlunoDTO alunoDTO) {
        return service.save(alunoDTO);
    }

    @PutMapping
    public AlunoDTO update(@RequestBody AlunoDTO alunoDTO) {
        return service.save(alunoDTO);
    }

    @GetMapping
    public List<AlunoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AlunoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}
