package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends
        JpaRepository<Editora, Long> {

    boolean existsByEmail(String email);

    boolean cnpjExists(String cnpj);

    boolean existsByEmailAndIdNot(String email, Long id);
}
