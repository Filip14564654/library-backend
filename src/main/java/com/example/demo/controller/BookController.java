package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000") // Povolení komunikace s Reactem
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    // Získání všech knih
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Přidání nové knihy
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        book.setAvailable(true); // Nová kniha je dostupná
        return bookRepository.save(book);
    }

    // Úprava existující knihy
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Smazání knihy
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    // Půjčení knihy uživateli
    @PostMapping("/{bookId}/borrow")
    public Loan borrowBook(@PathVariable Long bookId, @RequestParam String userName) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!book.getAvailable()) {
                throw new RuntimeException("Kniha je již vypůjčená.");
            }

            Loan loan = new Loan(userName, book, LocalDate.now(), null);
            book.setAvailable(false);
            bookRepository.save(book);

            return loanRepository.save(loan);
        } else {
            throw new RuntimeException("Kniha nebyla nalezena.");
        }
    }

    // Vrácení knihy
    @PutMapping("/return/{loanId}")
    public Loan returnBook(@PathVariable Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);

        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setReturnDate(LocalDate.now());

            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);

            return loanRepository.save(loan);
        } else {
            throw new RuntimeException("Výpůjčka nebyla nalezena.");
        }
    }
}
