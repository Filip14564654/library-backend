package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    // List all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // List all loans for a specific user
    public List<Loan> getLoansByUser(String userName) {
        return loanRepository.findByUserName(userName);
    }

    // Loan a book
    public Loan createLoan(Long bookId, String userName) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!book.getAvailable()) {
                throw new RuntimeException("Book is already borrowed.");
            }

            Loan loan = new Loan(userName, book, LocalDate.now(), null);
            book.setAvailable(false);
            bookRepository.save(book);

            return loanRepository.save(loan);
        } else {
            throw new RuntimeException("Book not found.");
        }
    }

    // Return a book
    public Loan returnBook(Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);

        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setReturnDate(LocalDate.now());

            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);

            return loanRepository.save(loan);
        } else {
            throw new RuntimeException("Loan not found.");
        }
    }

    // Get loan history for a book
    public List<Loan> getLoanHistory(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }
}
