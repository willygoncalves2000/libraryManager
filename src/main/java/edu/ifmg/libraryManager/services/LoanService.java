package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.dtos.LoanDTO;
import edu.ifmg.libraryManager.entities.Book;
import edu.ifmg.libraryManager.entities.Loan;
import edu.ifmg.libraryManager.entities.User;
import edu.ifmg.libraryManager.repository.BookRepository;
import edu.ifmg.libraryManager.repository.LoanRepository;
import edu.ifmg.libraryManager.repository.UserRepository;
import edu.ifmg.libraryManager.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @Transactional(readOnly = true)
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LoanDTO findById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan with id " + id + " not found"));
        return new LoanDTO(loan);
    }

    @Transactional
    public LoanDTO create(LoanDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Verifica se há unidades do livro disponível
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Não há cópias disponíveis para empréstimo");
        }
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getDueDate());
        loan.setReturnDate(dto.getReturnDate());

        // Decrementa uma cópia disponível
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    @Transactional
    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Livro já foi devolvido");
        }

        loan.setReturnDate(java.time.LocalDate.now());

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    @Transactional
    public void delete(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new RuntimeException("Loan with id " + loanId + " not found");
        }
        try {
            loanRepository.deleteById(loanId);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


}
