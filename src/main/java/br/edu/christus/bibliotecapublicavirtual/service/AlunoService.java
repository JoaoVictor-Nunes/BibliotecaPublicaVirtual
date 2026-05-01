package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import br.edu.christus.bibliotecapublicavirtual.repository.AlunoRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;

    public Aluno save(Aluno aluno) {
        if (aluno.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome não pode ter mais de 255 caracteres.");
        }
        // boolean bookExists;
//        if (u.getId() == null) {
//            emailExists = repository.existsByEmail(u.getEmail());
//        } else {
//            emailExists =
//        }
//        if (emailExists) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este email já está sendo utilizado.");
//        }

        return repository.save(MapperUtil.parseObject(aluno, Aluno.class));
    }

    public List<Aluno> findAll() {
        return repository.findAll();
    }

    public Aluno findById(Long id) {
        var aluno = repository.findById(id);
        if (aluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Esse aluno não existe.");
        }
        return MapperUtil.parseObject(aluno.get(), Aluno.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
