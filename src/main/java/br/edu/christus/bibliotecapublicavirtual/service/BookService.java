package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.repository.BookRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book save (Book book) {
        if (book.getTitle().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pode exceder 255 caracteres");
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

        return repository.save(MapperUtil.parseObject(book, Book.class));
    }

    public List<Book> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), Book.class);
    }

    public Book findByIsbn(Long isbn) {
        var book = repository.findById(isbn);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe livro com esse ISBN");
        }
        return MapperUtil.parseObject(book.get(), Book.class);
    }

    public void delete(Long isbn) {
        repository.deleteById(isbn);
    }
}
