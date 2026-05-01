package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean emailExists(String email);
}
