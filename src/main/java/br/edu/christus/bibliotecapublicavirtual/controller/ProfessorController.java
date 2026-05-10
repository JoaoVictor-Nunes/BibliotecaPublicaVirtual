package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.ProfessorDTO;
import br.edu.christus.bibliotecapublicavirtual.service.ProfessorService;
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
@RequestMapping("/api/v1/professor")
@Tag(name = "Professor", description = "Endpoints para gerenciamento de Professores da biblioteca")
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @Operation(summary = "Cadastra um novo professor", description = "Recebe os dados de um professor e o salva no banco de dados. Professores podem ter permissões especiais de empréstimo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Professor cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou erro de validação", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorDTO create(@RequestBody ProfessorDTO professorDTO) {
        return service.save(professorDTO);
    }

    @Operation(summary = "Atualiza um professor existente", description = "Recebe os dados atualizados de um professor e os salva no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado pelo ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping
    public ProfessorDTO update(@RequestBody ProfessorDTO professorDTO) {
        return service.save(professorDTO);
    }

    @Operation(summary = "Lista todos os professores", description = "Retorna uma lista contendo todos os professores cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso")
    })
    @GetMapping
    public List<ProfessorDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Busca um professor pelo ID", description = "Retorna os detalhes de um professor específico com base no seu identificador único (ID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor encontrado e retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado na base de dados", content = @Content)
    })
    @GetMapping("/{id}")
    public ProfessorDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Exclui um professor", description = "Remove um professor do banco de dados pelo seu ID. Operação irreversível.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Professor excluído com sucesso (Sem conteúdo de retorno)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado para exclusão", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
