package com.pkxd.infra.dynamoDB

import com.pkxd.data.interfaces.repository.ItemRepository
import com.pkxd.model.Item
import com.pkxd.utils.errors.EntityNotFoundException
import com.typesafe.config.Config
import kotlinx.coroutines.future.await
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest
import java.util.UUID
import java.util.concurrent.CompletionException

class DynamoDBItemRepository(config: Config, private val dynamoDbClient: DynamoDbAsyncClient) : ItemRepository {
  private val tableName = config.getString("dynamoDB.item.table-name")
  override suspend fun add(item: Item) {
    val addItem = mutableMapOf<String, AttributeValue>()
    addItem["id"] = AttributeValue.builder().s(item.id.toString()).build()
    addItem["coins"] = AttributeValue.builder().n(item.coins.toString()).build()
    addItem["gems"] = AttributeValue.builder().n(item.gems.toString()).build()
    addItem["image"] = AttributeValue.builder().s(item.image).build()
    val putItemRequest = PutItemRequest.builder().tableName(tableName).item(addItem).build()
    dynamoDbClient.putItem(putItemRequest)
  }

  override suspend fun update(item: Item) {
    try {
      try {
        val itemKey = mutableMapOf<String, AttributeValue>()
        itemKey["id"] = AttributeValue.builder().s(item.id.toString()).build()
        val updateValues = mutableMapOf<String, AttributeValue>()
        updateValues[":coins"] = AttributeValue.builder().n(item.coins.toString()).build()
        updateValues[":gems"] = AttributeValue.builder().n(item.gems.toString()).build()
        updateValues[":image"] = AttributeValue.builder().s(item.image).build()
        val updateItemRequest = UpdateItemRequest
          .builder()
          .tableName(tableName)
          .key(itemKey)
          .updateExpression("SET image = :image, coins = :coins, gems = :gems")
          .expressionAttributeValues(updateValues)
          .conditionExpression("attribute_exists(id)")
          .build()
        dynamoDbClient.updateItem(updateItemRequest).await()
      } catch (e: CompletionException) {
        throw e.cause ?: e
      }
    } catch (e: ConditionalCheckFailedException) {
      throw EntityNotFoundException("Item ${item.id.toString()} not found")
    }
  }

  override suspend fun delete(id: UUID) {
    try {
      try {
        val deleteItem = mutableMapOf<String, AttributeValue>()
        deleteItem["id"] = AttributeValue.builder().s(id.toString()).build()
        val deleteItemRequest = DeleteItemRequest.builder()
          .tableName(tableName)
          .key(deleteItem)
          .conditionExpression("attribute_exists(id)")
          .build()
        dynamoDbClient.deleteItem(deleteItemRequest).await()
      } catch (e: CompletionException) {
        throw e.cause ?: e
      }
    } catch (e: ConditionalCheckFailedException) {
      throw EntityNotFoundException("Item ${id.toString()} not found")
    }
  }
}