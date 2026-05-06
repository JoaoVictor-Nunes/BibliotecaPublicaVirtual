package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Professor> findByEmail(String email);
}
