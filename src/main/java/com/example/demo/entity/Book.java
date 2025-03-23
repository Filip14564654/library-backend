package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    private Boolean available = true;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<Loan> loanHistory;

    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @JsonIgnore
    public List<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void setLoanHistory(List<Loan> loanHistory) {
        this.loanHistory = loanHistory;
    }
}
