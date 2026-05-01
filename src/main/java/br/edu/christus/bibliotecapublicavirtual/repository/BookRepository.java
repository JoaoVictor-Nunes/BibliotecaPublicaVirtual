package br.edu.christus.bibliotecapublicavirtual.repository;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
}
