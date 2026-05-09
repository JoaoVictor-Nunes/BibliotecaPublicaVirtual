package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.BookDTO;
import br.edu.christus.bibliotecapublicavirtual.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> create(
            @RequestPart("book") BookDTO bookDTO,
            @RequestPart("file") MultipartFile file) {

        BookDTO savedBook = service.save(bookDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> update(
            @RequestPart("book") BookDTO bookDTO,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(service.save(bookDTO, file));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> findByISBN(@PathVariable Long isbn) {
        return ResponseEntity.ok(service.findByIsbn(isbn));
    }

    @GetMapping("/{isbn}/download")
    public ResponseEntity<String> getDownloadUrl(@PathVariable Long isbn) {
        BookDTO book = service.findByIsbn(isbn);

        String url = service.getDownloadUrl(book.getPdfKey());

        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long isbn) {
        service.delete(isbn);
    }
}