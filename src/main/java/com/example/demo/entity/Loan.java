package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference // ✅ Zabrání cyklické serializaci
    private Book book;

    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan() {}

    public Loan(String userName, Book book, LocalDate loanDate, LocalDate returnDate) {
        this.userName = userName;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
