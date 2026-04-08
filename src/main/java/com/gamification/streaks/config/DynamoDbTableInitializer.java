package com.gamification.streaks.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamoDbTableInitializer {

    private static final Logger log = LoggerFactory.getLogger(DynamoDbTableInitializer.class);

    private final AmazonDynamoDB amazonDynamoDB;

    public DynamoDbTableInitializer(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @PostConstruct
    public void createTablesIfNotExist() {
        List<TableDefinition> tables = List.of(
            new TableDefinition("Badge",            "badgeId"),
            new TableDefinition("Challenge",        "challengeId"),
            new TableDefinition("GamificationRule", "RuleId"),
            new TableDefinition("LeaderBoardEntry", "LeaderboardId"),
            new TableDefinition("RewardRule",       "ruleId"),
            new TableDefinition("StreakRecord",     "streakId"),
            new TableDefinition("UserBadge",        "userBadgeId"),
            new TableDefinition("UserGamificationProfile", "userId"),
            new TableDefinition("XpTransaction",    "xpTransactionId")
        );

        List<String> existing;
        try {
            existing = amazonDynamoDB.listTables().getTableNames();
        } catch (Exception e) {
            log.warn("DynamoDB not available, skipping table creation: {}", e.getMessage());
            return;
        }

        for (TableDefinition table : tables) {
            if (existing.contains(table.name)) {
                log.info("Table already exists: {}", table.name);
                continue;
            }
            try {
                amazonDynamoDB.createTable(new CreateTableRequest()
                    .withTableName(table.name)
                    .withAttributeDefinitions(
                        new AttributeDefinition(table.hashKey, ScalarAttributeType.S))
                    .withKeySchema(
                        new KeySchemaElement(table.hashKey, KeyType.HASH))
                    .withBillingMode(BillingMode.PAY_PER_REQUEST));
                log.info("Created table: {}", table.name);
            } catch (Exception e) {
                log.error("Failed to create table {}: {}", table.name, e.getMessage());
            }
        }
    }

    private record TableDefinition(String name, String hashKey) {}
}
