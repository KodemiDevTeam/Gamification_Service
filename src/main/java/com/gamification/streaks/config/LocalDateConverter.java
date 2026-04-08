package com.gamification.streaks.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.LocalDate;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    @Override
    public String convert(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @Override
    public LocalDate unconvert(String s) {
        return s == null ? null : LocalDate.parse(s);
    }
}
