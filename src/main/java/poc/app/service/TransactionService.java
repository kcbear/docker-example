package poc.app.service;

import poc.app.model.Transaction;
import poc.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    protected TransactionService() {
    }

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> listTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    public List<Transaction> listTransactionsByAccountNumberOrderByField(String accountNumber, Sort.Direction direction, String orderBy) {
        return transactionRepository.findByAccountNumber(accountNumber, Sort.by(direction, orderBy));
    }

    public Page<Transaction> listTransactionsWithPageByAccountNumber(String accountNumber, Pageable pageable) {
        return transactionRepository.findByAccountNumber(accountNumber, pageable);
    }

    public Page<Transaction> listFirstPageOfTransactionsOrderByValueDateDesc(String accountNumber, int noOfResult) {
        String orderBy = "valueDate";
        Pageable pageable = PageRequest.of(0, noOfResult, Sort.by(Sort.Direction.DESC, orderBy));

        return transactionRepository.findByAccountNumber(accountNumber, pageable);
    }

}
