package com.pkxd.infra.dynamoDB

import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.domain.model.Profile
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

class DynamoDBRepository(private val dynamoDbClient: DynamoDbAsyncClient) : ProfileRepository {
  private val tableName = "profile"
  override suspend fun add(profile: Profile) {
    try {
      val profileItem = mutableMapOf<String, AttributeValue>()
      profileItem["id"] = AttributeValue.builder().s(profile.id.toString()).build()
      profileItem["email"] = AttributeValue.builder().s(profile.email).build()
      profileItem["nickname"] = AttributeValue.builder().s(profile.nickname).build()

      val putItemRequest = PutItemRequest.builder().tableName(tableName).item(profileItem).build()
      dynamoDbClient.putItem(putItemRequest)
    } catch (e: Exception) {
      println(e.message)
    }
  }
}