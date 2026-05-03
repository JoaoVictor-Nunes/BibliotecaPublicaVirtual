package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.ProfessorDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import br.edu.christus.bibliotecapublicavirtual.repository.ProfessorRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        Optional<Professor> existingByEmail = repository.findByEmail(professorDTO.getEmail());
        if (existingByEmail.isPresent() && (professorDTO.getId() == null || !existingByEmail.get().getId().equals(professorDTO.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este email já está sendo utilizado.");
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
