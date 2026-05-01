package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import br.edu.christus.bibliotecapublicavirtual.repository.ProfessorRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repository;

    public Professor save(Professor professor) {
        if (professor.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome do professor não pode ter mais que 255 caracteres.");
        }

        return repository.save(MapperUtil.parseObject(professor, Professor.class));
    }

    public List<Professor> findAll() {
        return repository.findAll();
    }

    public Professor findById(Long id) {
        var professor = repository.findById(id);
        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "O professor não existe.");
        }
        return MapperUtil.parseObject(professor.get(), Professor.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
