package com.fuelgo.cloud.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

import java.io.IOException;

@AllArgsConstructor
public class PumpMessageConverter extends AbstractMessageConverter {

    private final ObjectMapper jsonMapper;

    @Override
    protected boolean supports(Class<?> clazz) {
        return PumpMessage.class == clazz;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        if (byte[].class == getSerializedPayloadClass()) {
            try {
                return jsonMapper.readValue((byte[]) message.getPayload(), targetClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return message.getPayload();
    }

    @Override
    @Nullable
    protected Object convertToInternal(
            Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {

        if (byte[].class == getSerializedPayloadClass()) {
            try {
                payload = jsonMapper.writeValueAsBytes(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return payload;
    }

}
