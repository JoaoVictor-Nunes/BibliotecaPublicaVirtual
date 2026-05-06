package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.BookDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.repository.BookRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public BookDTO save (BookDTO bookDTO) {
        if (bookDTO.getTitle().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pode exceder 255 caracteres");
        }
        if (bookDTO.getAnoLancamento() > Year.now().getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ano de lançamento não pode ser maior que o atual.");
        }
        Optional<Book> existingByTitle = repository.findByTitle(bookDTO.getTitle());
        if (existingByTitle.isPresent() && (bookDTO.getIsbn() == null || !existingByTitle.get().getIsbn().equals(bookDTO.getIsbn()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este título já está sendo utilizado.");
        }

        Book bookToSave = MapperUtil.parseObject(bookDTO, Book.class);
        Book savedBook = repository.save(bookToSave);
        return MapperUtil.parseObject(savedBook, BookDTO.class);
    }

    public List<BookDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), BookDTO.class);
    }

    public BookDTO findByIsbn(Long isbn) {
        var book = repository.findById(isbn);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe livro com esse ISBN");
        }
        return MapperUtil.parseObject(book.get(), BookDTO.class);
    }

    public void delete(Long isbn) {
        repository.deleteById(isbn);
    }
}
