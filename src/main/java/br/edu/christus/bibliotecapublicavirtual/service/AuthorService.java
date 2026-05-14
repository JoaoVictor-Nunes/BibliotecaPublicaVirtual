package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AuthorDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Author;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.repository.AuthorRepository;
import br.edu.christus.bibliotecapublicavirtual.repository.BookRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
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

        Author authorEntity = MapperUtil.parseObject(authorDTO, Author.class);

        if (authorDTO.getLivrosIds() != null && !authorDTO.getLivrosIds().isEmpty()) {
            List<Book> livrosGerenciados = authorDTO.getLivrosIds().stream()
                    .map(id -> bookRepository.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Livro com ISBN " + id + " não encontrado no sistema.")))
                    .collect(Collectors.toList());

            authorEntity.setLivros(livrosGerenciados);
        } else {
            authorEntity.setLivros(null);
        }

        var authorSaved = repository.save(authorEntity);

        AuthorDTO responseDTO = MapperUtil.parseObject(authorSaved, AuthorDTO.class);

        if (authorSaved.getLivros() != null) {
            List<Long> idsSalvos = authorSaved.getLivros().stream()
                    .map(Book::getIsbn)
                    .collect(Collectors.toList());
            responseDTO.setLivrosIds(idsSalvos);
        }

        return responseDTO;
    }

    public List<AuthorDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), AuthorDTO.class);
    }

    public AuthorDTO findByID(Long id) {
        var author = repository.findById(id);
        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe autor com esse id.");
        }
        return MapperUtil.parseObject(author.get(), AuthorDTO.class);
    }

    public void delete(Long id) {
        var author = repository.findById(id);
        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe autor com esse id.");
        }
        repository.deleteById(id);
    }
}