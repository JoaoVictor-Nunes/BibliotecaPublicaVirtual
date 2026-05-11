package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AuthorDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Author;
import br.edu.christus.bibliotecapublicavirtual.repository.AuthorRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository repository;

    public AuthorDTO save(AuthorDTO authorDTO) {
        if (authorDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome do autor não pode ter mais que 255 caracteres");
        }

        boolean EmailExists;

        if (authorDTO.getId() == null) {
            EmailExists = repository.existsByEmail(authorDTO.getEmail());
        } else {
            EmailExists = repository.existsByEmailAndIdNot(authorDTO.getEmail(), authorDTO.getId());
        }

        if (EmailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este e-mail já está sendo utilizado.");
        }

        var authorSaved = repository.save(MapperUtil.parseObject(authorDTO, Author.class));
        return MapperUtil.parseObject(authorSaved, AuthorDTO.class);
    }

    public List<AuthorDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), AuthorDTO.class);
    }

    public AuthorDTO findByID(Long id) {
        var author = repository.findById(id);
        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe autor com esse nome.");
        }
        return MapperUtil.parseObject(author.get(), AuthorDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
