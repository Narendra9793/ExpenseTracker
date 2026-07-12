package org.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.authservice.eventProducer.UserInfoEvent;
import org.authservice.models.UserInfoDto;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoEvent> {
    @Override
    public byte[] serialize(String topic, UserInfoEvent data) {
        byte[] retVel= null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            retVel = mapper.writeValueAsString(data).getBytes();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVel;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }


    @Override
    public void close() {
    }
}
