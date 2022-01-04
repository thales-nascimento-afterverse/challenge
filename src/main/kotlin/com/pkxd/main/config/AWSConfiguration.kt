package com.pkxd.main.config

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI


object AWSConfiguration {
  val dynamoDBClient: DynamoDbAsyncClient = DynamoDbAsyncClient.builder()
    //.region(Region.US_EAST_1)
    .endpointOverride(URI.create("http://localhost:8042"))
    .build()
}