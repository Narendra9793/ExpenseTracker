package com.npsinghdevhb.expenseService.services;

import com.npsinghdevhb.expenseService.DTOs.ExpenseDTO;
import com.npsinghdevhb.expenseService.entities.Expense;
import com.npsinghdevhb.expenseService.mappers.ExpenseMapper;
import com.npsinghdevhb.expenseService.repositories.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final  ExpenseMapper mapper;


    @Autowired
    ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper mapper){
        this.expenseRepository= expenseRepository;
        this.mapper= mapper;
    }



    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        setCurrency(expenseDTO);
        Expense expense= expenseRepository.save(mapper.toEntity(expenseDTO));
        return mapper.toDTO(expense);
    }

    public  boolean updateExpense(ExpenseDTO expenseDTO){
        Optional<Expense> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(), expenseDTO.getExternalId());
        if(expenseFoundOpt.isEmpty()){
            return false;
        }
        Expense expense = expenseFoundOpt.get();
        expense.setCurrency(Strings.isNotBlank(expenseDTO.getCurrency())? expenseDTO.getCurrency() : expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDTO.getMerchant())?expenseDTO.getMerchant() : expense.getMerchant());
        expense.setAmount(expenseDTO.getAmount());
        expenseRepository.save(expense);
        return true;
    }

    public  boolean deleteExpense(String userId, ExpenseDTO expenseDTO){
        Optional<Expense> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(), expenseDTO.getExternalId());
        if(expenseFoundOpt.isEmpty()){
            return false;
        }
        Expense expense = expenseFoundOpt.get();
        if(userId.equals(expense.getUserId())){
            expenseRepository.delete(expense);
        }
        else {
            return false;
        }

        return true;
    }

    public List<ExpenseDTO> getExpense(String userId){
        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        return expenseList.stream()
                .map(mapper :: toDTO)
                .toList();
    }
    private void setCurrency(ExpenseDTO expenseDTO){
        if(Objects.isNull(expenseDTO.getCurrency())){
            expenseDTO.setCurrency("inr");
        }
    }
}
