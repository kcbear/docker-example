package poc.app.repository;

import poc.app.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String number);

    List<Transaction> findByAccountNumber(String number, Sort sort);

    Page<Transaction> findByAccountNumber(String number, Pageable pageable);

}
