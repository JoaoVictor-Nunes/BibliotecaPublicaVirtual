package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AlunoDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import br.edu.christus.bibliotecapublicavirtual.repository.AlunoRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;

    public AlunoDTO save(AlunoDTO alunoDTO) {
        if (alunoDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome não pode ter mais de 255 caracteres.");
        }

        Optional<Aluno> existingByEmail = repository.findByEmail(alunoDTO.getEmail());
        if (existingByEmail.isPresent() && (alunoDTO.getId() == null || !existingByEmail.get().getId().equals(alunoDTO.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este email já está sendo utilizado.");
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
        repository.deleteById(id);
    }
}
