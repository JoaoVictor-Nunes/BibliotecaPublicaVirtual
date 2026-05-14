package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AlunoDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import br.edu.christus.bibliotecapublicavirtual.repository.AlunoRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Serie;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;

    public AlunoDTO save(AlunoDTO alunoDTO) {
        if (alunoDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome não pode ter mais de 255 caracteres.");
        }
        if (!alunoDTO.getSerie().equals(Serie.PRIMEIRO_ANO)
                && !alunoDTO.getSerie().equals(Serie.SEGUNDO_ANO)
                && !alunoDTO.getSerie().equals(Serie.TERCEIRO_ANO))  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O aluno está no ensino médio (1o, 2o ou 3o serie).");
        }
        boolean EmailExists;

        if (alunoDTO.getId() == null) {
            EmailExists = repository.existsByEmail(alunoDTO.getEmail());
        } else {
            EmailExists = repository.existsByEmailAndIdNot(alunoDTO.getEmail(), alunoDTO.getId());
        }

        if (EmailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este e-mail já está sendo utilizado.");
        }

        Aluno alunoToSave = MapperUtil.parseObject(alunoDTO, Aluno.class);
        Aluno savedAluno = repository.save(alunoToSave);
        return MapperUtil.parseObject(savedAluno, AlunoDTO.class);
    }

    public List<AlunoDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), AlunoDTO.class);
    }

    public AlunoDTO findById(Long id) {
        var aluno = repository.findById(id);
        if (aluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Esse aluno não existe.");
        }
        return MapperUtil.parseObject(aluno.get(), AlunoDTO.class);
    }

    public void delete(Long id) {
        var aluno = repository.findById(id);
        if (aluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Esse aluno não existe.");
        }
        repository.deleteById(id);
    }
}
