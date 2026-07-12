package com.npsinghdevhb.expenseService.consumer;

import com.npsinghdevhb.expenseService.DTOs.ExpenseDTO;
import com.npsinghdevhb.expenseService.services.ExpenseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseConsumer(ExpenseService expenseService){
        this.expenseService=expenseService;
    }

    @PostConstruct
    public void init() {
        System.out.println("DsServiceConsumer bean created");
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ExpenseDTO expeneDTO) {
        try{
            // Todo: Make it transactional, to handle idempotency and validate email, phoneNumber etc
            expenseService.createExpense(expeneDTO);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println(" Exception is thrown while consuming kafka event");
        }
    }
}
