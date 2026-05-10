package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.BookDTO;
import br.edu.christus.bibliotecapublicavirtual.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Livro", description = "Endpoints para gerenciamento de Livros e upload de arquivos PDF")
public class BookController {

    @Autowired
    private BookService service;

    @Operation(summary = "Cadastra um novo livro com arquivo PDF", description = "Recebe os dados do livro juntamente com o arquivo PDF (multipart/form-data). O livro é salvo no banco e o arquivo enviado para armazenamento em nuvem.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados do livro inválidos ou falha no upload do arquivo", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> create(
            @RequestPart("book") BookDTO bookDTO,
            @RequestPart("file") MultipartFile file) {

        BookDTO savedBook = service.save(bookDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(summary = "Atualiza um livro existente com novo PDF", description = "Recebe os dados atualizados do livro e um novo arquivo PDF. O arquivo antigo será substituído se existir.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado pelo ISBN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados do livro inválidos ou erro no arquivo", content = @Content)
    })
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> update(
            @RequestPart("book") BookDTO bookDTO,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(service.save(bookDTO, file));
    }

    @Operation(summary = "Lista todos os livros", description = "Retorna uma lista contendo todos os livros cadastrados no acervo da biblioteca.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Busca um livro pelo ISBN", description = "Traz as informações detalhadas de um livro específico utilizando o ISBN como identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado e retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado no acervo", content = @Content)
    })
    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> findByISBN(@PathVariable Long isbn) {
        return ResponseEntity.ok(service.findByIsbn(isbn));
    }

    @Operation(summary = "Obtém link de download do PDF", description = "Gera e retorna uma URL temporária ou permanente para download do arquivo PDF associado ao livro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "URL gerada com sucesso",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "https://meubucket.s3.amazonaws.com/livro.pdf"))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado ou sem arquivo PDF associado", content = @Content)
    })
    @GetMapping("/{isbn}/download")
    public ResponseEntity<String> getDownloadUrl(@PathVariable Long isbn) {
        BookDTO book = service.findByIsbn(isbn);

        String url = service.getDownloadUrl(book.getPdfKey());

        return ResponseEntity.ok(url);
    }

    @Operation(summary = "Exclui um livro", description = "Remove um livro do banco de dados pelo seu ISBN. Note que isso pode excluir ou deixar órfão o PDF salvo na nuvem.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso (Sem conteúdo de retorno)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado para exclusão", content = @Content)
    })
    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long isbn) {
        service.delete(isbn);
    }
}