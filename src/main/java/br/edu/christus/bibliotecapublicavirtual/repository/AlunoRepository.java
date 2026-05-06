package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Aluno> findByEmail(String email);
}
