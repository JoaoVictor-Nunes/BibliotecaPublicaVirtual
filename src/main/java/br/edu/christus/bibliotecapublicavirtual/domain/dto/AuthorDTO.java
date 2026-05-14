package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(example = "{\n" +
        "  \"name\": \"Editora Teste\",\n" +
        "  \"email\": \"teste@gmai.com\",\n" +
        "  \"biografia\": \"Lorem Ipsum\",\n" +
        "  \"livrosIds\": [\n" +
        "      1\n" +
        "  ]\n" +
        "}")
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private String biografia;
    private String nacionalidade;
    private List<Long> livrosIds;
}
