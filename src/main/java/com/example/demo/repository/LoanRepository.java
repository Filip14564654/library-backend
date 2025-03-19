package com.example.demo.repository;

import com.example.demo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserName(String userName);
    List<Loan> findByBookId(Long bookId);
}
