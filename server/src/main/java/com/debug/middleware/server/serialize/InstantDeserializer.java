package com.debug.middleware.server.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>
 * 自定义 LocalDateTime 反序列化
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
public class InstantDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return Instant.ofEpochMilli(jsonParser.getLongValue()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
