package edu.ifmg.libraryManager.dtos;

import edu.ifmg.libraryManager.entities.Loan;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class LoanDTO extends RepresentationModel<LoanDTO> {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LoanDTO() {
    }

    public LoanDTO(Long id, Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public LoanDTO(Loan entity) {
        this.id = entity.getId();
        this.userId = entity.getUser() != null ? entity.getUser().getId() : null;
        this.bookId = entity.getBook() != null ? entity.getBook().getId() : null;
        this.loanDate = entity.getLoanDate();
        this.dueDate = entity.getDueDate();
        this.returnDate = entity.getReturnDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}