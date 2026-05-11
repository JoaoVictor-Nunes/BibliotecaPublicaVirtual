package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.EditoraDTO;
import br.edu.christus.bibliotecapublicavirtual.service.EditoraService;
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
@RequestMapping("/api/v1/editora")
@Tag(name = "Editora", description = "Endpoints para gerenciamento de Editoras dos livros")
public class EditoraController {
    @Autowired
    private EditoraService service;

    @Operation(summary = "Cadastra uma nova editora", description = "Registra uma nova editora no sistema. Editoras são vinculadas a um ou mais livros.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Editora cadastrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditoraDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou dados incorretos", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditoraDTO create(@RequestBody EditoraDTO editoraDTO) {
        return service.save(editoraDTO);
    }

    @Operation(summary = "Atualiza uma editora existente", description = "Atualiza os dados cadastrais de uma editora, como razão social ou contato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditoraDTO.class))),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada pelo ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping
    public EditoraDTO update(@RequestBody EditoraDTO editoraDTO) {
        return service.save(editoraDTO);
    }

    @Operation(summary = "Lista todas as editoras", description = "Retorna uma coleção com todas as editoras do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de editoras retornada com sucesso")
    })
    @GetMapping
    public List<EditoraDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Busca uma editora pelo ID", description = "Traz as informações detalhadas de uma editora específica pesquisada pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada e retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditoraDTO.class))),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada na base de dados", content = @Content)
    })
    @GetMapping("/{id}")
    public EditoraDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Exclui uma editora", description = "Remove uma editora do sistema baseado no seu ID. Não deve ser possível se houver livros atrelados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Editora excluída com sucesso (Sem conteúdo de retorno)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada para exclusão", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: a editora possui livros atrelados e não pode ser excluída", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
