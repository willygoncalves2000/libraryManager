package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.dtos.BookDTO;
import edu.ifmg.libraryManager.entities.Book;
import edu.ifmg.libraryManager.repository.BookRepository;
import edu.ifmg.libraryManager.services.exceptions.DatabaseException;
import edu.ifmg.libraryManager.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return new BookDTO(book);
    }

    @Transactional
    public BookDTO create(BookDTO dto) {
        Book book = new Book();
        copyDtoToEntity(dto, book);
        bookRepository.save(book);
        return new BookDTO(book);
    }

    @Transactional
    public BookDTO update(Long id, BookDTO dto) {
        try {
            Book book = bookRepository.getReferenceById(id);
            copyDtoToEntity(dto, book);
            book = bookRepository.save(book);
            return new BookDTO(book);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Book not found with id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFound("Book not found with id: " + id);
        } try {
            bookRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    private void copyDtoToEntity(BookDTO dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
        entity.setTotalCopies(dto.getTotalCopies());
        entity.setAvailableCopies(dto.getAvailableCopies());
    }
}
