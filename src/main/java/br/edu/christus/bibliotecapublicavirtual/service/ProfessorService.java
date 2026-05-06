package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.ProfessorDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import br.edu.christus.bibliotecapublicavirtual.repository.ProfessorRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Serie;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Disciplina;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repository;

    public ProfessorDTO save(ProfessorDTO professorDTO) {
        if (professorDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome do professor não pode ter mais que 255 caracteres.");
        }

        if (!professorDTO.getDisciplina().equals(Disciplina.MATEMATICA)
                && !professorDTO.getDisciplina().equals(Disciplina.PORTUGUES)
                && !professorDTO.getDisciplina().equals(Disciplina.EDUCACAO_FISICA)
                && !professorDTO.getDisciplina().equals(Disciplina.HISTORIA)
                && !professorDTO.getDisciplina().equals(Disciplina.GEOGRAFIA)
                && !professorDTO.getDisciplina().equals(Disciplina.QUIMICA)
                && !professorDTO.getDisciplina().equals(Disciplina.FISICA)
                && !professorDTO.getDisciplina().equals(Disciplina.BIOLOGIA)
                && !professorDTO.getDisciplina().equals(Disciplina.INGLES)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Disciplina inválida.");
        }
        if (!professorDTO.getSerie().equals(Serie.PRIMEIRO_ANO)
                && !professorDTO.getSerie().equals(Serie.SEGUNDO_ANO)
                && !professorDTO.getSerie().equals(Serie.TERCEIRO_ANO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O professor da aula no ensino médio (1o, 2o ou 3o serie).");
        }

        boolean EmailExists;

        if (professorDTO.getId() == null) {
            EmailExists = repository.existsByEmail(professorDTO.getEmail());
        } else {
            EmailExists = repository.existsByEmailAndIdNot(professorDTO.getEmail(), professorDTO.getId());
        }

        if (EmailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este e-mail já está sendo utilizado.");
        }

        Professor professorToSave = MapperUtil.parseObject(professorDTO, Professor.class);
        Professor savedProfessor = repository.save(professorToSave);
        return MapperUtil.parseObject(savedProfessor, ProfessorDTO.class);
    }

    public List<ProfessorDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), ProfessorDTO.class);
    }

    public ProfessorDTO findById(Long id) {
        var professor = repository.findById(id);
        if (professor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "O professor não existe.");
        }
        return MapperUtil.parseObject(professor.get(), ProfessorDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
