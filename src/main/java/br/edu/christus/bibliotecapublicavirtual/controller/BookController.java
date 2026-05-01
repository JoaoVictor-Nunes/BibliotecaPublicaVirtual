package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.save(book);
    }
    
    @PutMapping
    public Book update(@RequestBody Book book) {
        return service.save(book);
    }
    
    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{isbn}")
    public Book findByISBN(@PathVariable Long isbn) {
        return service.findByIsbn(isbn);
    }
    
    @DeleteMapping("/{isbn}")
    void delete(@PathVariable Long isbn) {
        service.delete(isbn);
    }
}
