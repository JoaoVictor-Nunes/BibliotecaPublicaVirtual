package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.EditoraDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Editora;
import br.edu.christus.bibliotecapublicavirtual.repository.EditoraRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository repository;

    public EditoraDTO save(EditoraDTO editoraDTO) {
        if (editoraDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome não pode ultrapassar 255 caracteres");
        }

        boolean EmailExists;

        if (editoraDTO.getId() == null) {
            EmailExists = repository.existsByEmail(editoraDTO.getEmail());
        } else {
            EmailExists = repository.existsByEmailAndIdNot(editoraDTO.getEmail(), editoraDTO.getId());
        }

        if (EmailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este e-mail já está sendo utilizado.");
        }

        boolean CNPJExists;

        if (editoraDTO.getCnpj() == null) {
                CNPJExists = repository.existsByEmail(editoraDTO.getEmail());
            } else {
                CNPJExists = repository.existsByEmailAndIdNot(editoraDTO.getEmail(), editoraDTO.getId());
            }

            if (CNPJExists) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Este CNPJ já está sendo utilizado.");
            }
        var editoraSaved = repository.save(MapperUtil.parseObject(editoraDTO, Editora.class));
        return MapperUtil.parseObject(editoraSaved, EditoraDTO.class);
    }

    public List<EditoraDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), EditoraDTO.class);
    }

    public EditoraDTO findById(Long id) {
        var editora = repository.findById(id);
        if (editora.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe editora com esse ID");
        }

        return MapperUtil.parseObject(editora.get(), EditoraDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
