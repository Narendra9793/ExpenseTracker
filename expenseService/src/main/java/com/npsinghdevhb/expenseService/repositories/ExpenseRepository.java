package com.npsinghdevhb.expenseService.repositories;

import com.npsinghdevhb.expenseService.entities.Expense;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense,Long > {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndCreatedAtBetween(String userId, Timestamp startTime, Timestamp endTime);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);
}
