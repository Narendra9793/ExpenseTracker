package com.npsinghdevhb.expenseService.mappers;

import com.npsinghdevhb.expenseService.DTOs.ExpenseDTO;
import com.npsinghdevhb.expenseService.entities.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDTO toDTO(Expense expense);
    Expense toEntity(ExpenseDTO expenseDTO);
}
