package com.example.demo.controller;

import com.example.demo.entity.Loan;
import com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Get all loans
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    // Get loans by user
    @GetMapping("/user/{userName}")
    public List<Loan> getLoansByUser(@PathVariable String userName) {
        return loanService.getLoansByUser(userName);
    }

    // Create a new loan
    @PostMapping("/borrow/{bookId}")
    public Loan createLoan(@PathVariable Long bookId, @RequestParam String userName) {
        return loanService.createLoan(bookId, userName);
    }

    // Return a book
    @PutMapping("/return/{loanId}")
    public Loan returnBook(@PathVariable Long loanId) {
        return loanService.returnBook(loanId);
    }

    // Get loan history for a specific book
    @GetMapping("/history/{bookId}")
    public List<Loan> getLoanHistory(@PathVariable Long bookId) {
        return loanService.getLoanHistory(bookId);
    }
}
