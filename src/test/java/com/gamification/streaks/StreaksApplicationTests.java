package com.gamification.streaks;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
class StreaksApplicationTests {

	@MockBean
	AmazonDynamoDB amazonDynamoDB;

	@MockBean
	DynamoDBMapper dynamoDBMapper;

	@Test
	void contextLoads() {
		when(amazonDynamoDB.listTables())
			.thenReturn(new ListTablesResult().withTableNames(Collections.emptyList()));
	}
}
