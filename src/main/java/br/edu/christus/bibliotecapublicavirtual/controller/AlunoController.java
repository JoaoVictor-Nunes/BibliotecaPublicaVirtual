package br.edu.christus.bibliotecapublicavirtual.controller;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.AlunoDTO;
import br.edu.christus.bibliotecapublicavirtual.service.AlunoService;
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
@RequestMapping("/api/v1/aluno")
@Tag(name = "Aluno", description = "Endpoints para gerenciamento de Alunos na biblioteca")
public class AlunoController {
    @Autowired
    private AlunoService service;

    @Operation(summary = "Cadastra um novo aluno", description = "Recebe os dados de um aluno e o salva no banco de dados. Ideal para o primeiro registro do usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou erro de validação", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoDTO create(@RequestBody AlunoDTO alunoDTO) {
        return service.save(alunoDTO);
    }

    @Operation(summary = "Atualiza um aluno existente", description = "Recebe os dados atualizados de um aluno e os salva no banco de dados. Útil para quando o aluno altera seu endereço, telefone, etc.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado pelo ID informado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping
    public AlunoDTO update(@RequestBody AlunoDTO alunoDTO) {
        return service.save(alunoDTO);
    }

    @Operation(summary = "Lista todos os alunos", description = "Retorna uma lista contendo todos os alunos cadastrados no sistema. Pode ser usado para popular tabelas ou relatórios gerais.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
    })
    @GetMapping
    public List<AlunoDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Busca um aluno pelo ID", description = "Retorna os detalhes de um aluno específico com base no seu identificador único (ID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aluno encontrado e retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado na base de dados", content = @Content)
    })
    @GetMapping("/{id}")
    public AlunoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Exclui um aluno", description = "Remove um aluno do banco de dados pelo seu ID. Operação irreversível.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso (Sem conteúdo de retorno)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado para exclusão", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}
