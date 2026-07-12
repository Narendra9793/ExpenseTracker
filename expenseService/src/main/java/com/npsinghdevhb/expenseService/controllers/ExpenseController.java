package com.npsinghdevhb.expenseService.controllers;

import com.npsinghdevhb.expenseService.DTOs.ExpenseDTO;
import com.npsinghdevhb.expenseService.entities.Expense;
import com.npsinghdevhb.expenseService.services.ExpenseService;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService ;

    ExpenseController(ExpenseService expenseService){

        this.expenseService = expenseService;
    }

    @GetMapping(path="/expense/v1/getExpense")
    public ResponseEntity<List<ExpenseDTO>>getExpenses(@RequestHeader(value= "X-User-Id") @NonNull String userId){
        try{
            List<ExpenseDTO> expenseDTOList=expenseService.getExpense(userId);
            return  new ResponseEntity<>(expenseDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="/expense/v1/updateExpense")
    public ResponseEntity<Boolean> updateExpense (@RequestBody  ExpenseDTO expenseDTO){
        try{
            return new ResponseEntity<>(expenseService.updateExpense(expenseDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path="/expense/v1/deleteExpense")
    public ResponseEntity<Boolean> deleteExpense ( @RequestHeader(value= "X-User-Id") @NonNull String userId, @RequestBody  ExpenseDTO expenseDTO){
        try{
            return new ResponseEntity<>(expenseService.deleteExpense(userId, expenseDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/expense/v1/createExpense")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestHeader("X-User-Id") String userId, @RequestBody ExpenseDTO expenseDTO) {
        expenseDTO.setUserId(userId);
        ExpenseDTO savedExpense = expenseService.createExpense(expenseDTO);

        if (savedExpense == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }
}
