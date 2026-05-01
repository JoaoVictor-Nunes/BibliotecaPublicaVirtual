package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean emailExists(String email);
}
