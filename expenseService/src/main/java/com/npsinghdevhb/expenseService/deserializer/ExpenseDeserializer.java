package com.npsinghdevhb.expenseService.deserializer;

import com.npsinghdevhb.expenseService.DTOs.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDTO> {
    @Override public void close() {
    }
    @Override public void configure(Map<String, ?> arg0, boolean arg1) {
    }

    @Override
    public ExpenseDTO deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        ExpenseDTO expenseDTO = null;
        try {
            expenseDTO = mapper.readValue(arg1, ExpenseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseDTO;
    }
}
