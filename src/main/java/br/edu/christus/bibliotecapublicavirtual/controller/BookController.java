package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.BookDTO;
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
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return service.save(bookDTO);
    }
    
    @PutMapping
    public BookDTO update(@RequestBody BookDTO bookDTO) {
        return service.save(bookDTO);
    }
    
    @GetMapping
    public List<BookDTO> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{isbn}")
    public BookDTO findByISBN(@PathVariable Long isbn) {
        return service.findByIsbn(isbn);
    }
    
    @DeleteMapping("/{isbn}")
    void delete(@PathVariable Long isbn) {
        service.delete(isbn);
    }
}
