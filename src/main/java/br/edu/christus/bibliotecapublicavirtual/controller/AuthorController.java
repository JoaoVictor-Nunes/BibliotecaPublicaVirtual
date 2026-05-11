package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AuthorDTO;
import br.edu.christus.bibliotecapublicavirtual.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@Tag(name = "Autor", description = "Endpoints para gerenciamento de Autores dos livros")
public class AuthorController {
    @Autowired
    private AuthorService service;

    @Operation(summary = "Cadastra um novo autor", description = "Recebe os dados de um autor e o salva no banco de dados. Necessário para vincular autores aos livros.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody AuthorDTO authorDTO) {
        return service.save(authorDTO);
    }

    @Operation(summary = "Atualiza um autor existente", description = "Atualiza os dados de um autor, como seu nome ou nacionalidade.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado pelo ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping
    public AuthorDTO update(@RequestBody AuthorDTO authorDTO) {
        return service.save(authorDTO);
    }

    @Operation(summary = "Lista todos os autores", description = "Retorna uma lista com todos os autores cadastrados. Útil para preenchimento de combos e seleções em tela.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso")
    })
    @GetMapping
    public List<AuthorDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Busca um autor pelo ID", description = "Traz as informações detalhadas de um autor específico pesquisado pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado e retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado na base de dados", content = @Content)
    })
    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        return service.findByID(id);
    }

    @Operation(summary = "Exclui um autor", description = "Remove um autor do sistema baseado no seu ID. Não deve ser possível se houver livros atrelados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso (Sem conteúdo de retorno)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado para exclusão", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: o autor possui livros atrelados e não pode ser excluído", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
