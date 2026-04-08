package com.gamification.streaks.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    @Override
    public String convert(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toString();
    }

    @Override
    public LocalDateTime unconvert(String s) {
        return s == null ? null : LocalDateTime.parse(s);
    }
}
