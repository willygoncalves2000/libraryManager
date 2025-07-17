package edu.ifmg.libraryManager.repository;

import edu.ifmg.libraryManager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // você pode adicionar métodos como:
    // List<Book> findByTitleContainingIgnoreCase(String title);
}
