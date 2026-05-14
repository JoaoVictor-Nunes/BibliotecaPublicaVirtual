package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(example = "{\n" +
        "  \"cnpj\": \"testeCNPJ\",\n" +
        "  \"name\": \"Editora Teste\",\n" +
        "  \"email\": \"teste@gmai.com\",\n" +
        "  \"endereco\": \"Endereco Falso\",\n" +
        "  \"livrosIds\": [\n" +
        "      1\n" +
        "  ]\n" +
        "}")
public class EditoraDTO {
    private Long id;
    private String cnpj;
    private String name;
    private String email;
    private String endereco;
    private List<Long> livrosIds;
}
