package com.pandaer.project.server.util.deserialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 反序列化器
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final String format = "yyyy-MM-dd HH:mm:ss";
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String time = jsonParser.getText();
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
    }
}
