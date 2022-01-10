package com.pkxd.infra.dynamoDB

import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.domain.model.Product
import com.pkxd.utils.errors.EntityNotFoundException
import com.typesafe.config.Config
import kotlinx.coroutines.future.await
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeAction
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest
import java.util.UUID
import java.util.concurrent.CompletionException

class DynamoDBProductsRepository(config: Config, private val dynamoDbClient: DynamoDbAsyncClient) : ProductRepository {
  private val tableName = config.getString("dynamoDB.product.table-name")
  override suspend fun add(product: Product) {
    val productItem = mutableMapOf<String, AttributeValue>()
    productItem["id"] = AttributeValue.builder().s(product.id.toString()).build()
    productItem["coins"] = AttributeValue.builder().n(product.coins.toString()).build()
    productItem["gems"] = AttributeValue.builder().n(product.gems.toString()).build()
    productItem["price"] = AttributeValue.builder().n(product.price.toString()).build()
    val putItemRequest = PutItemRequest.builder().tableName(tableName).item(productItem).build()
    dynamoDbClient.putItem(putItemRequest)
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
      throw EntityNotFoundException("Product ${id.toString()} not found")
    }
  }

  override suspend fun update(product: Product) {
    try {
      try {
        val itemKey = mutableMapOf<String, AttributeValue>()
        itemKey["id"] = AttributeValue.builder().s(product.id.toString()).build()
        val updatedValues = mutableMapOf<String, AttributeValue>()
        updatedValues[":coins"] = AttributeValue.builder().n(product.coins.toString()).build()
        updatedValues[":gems"] = AttributeValue.builder().n(product.gems.toString()).build()
        updatedValues[":price"] = AttributeValue.builder().n(product.price.toString()).build()
        val updateItemRequest = UpdateItemRequest
          .builder()
          .tableName(tableName)
          .key(itemKey)
          .updateExpression("SET price = :price, coins = :coins, gems = :gems")
          .expressionAttributeValues(updatedValues)
          .conditionExpression("attribute_exists(id)")
          .build()
        dynamoDbClient.updateItem(updateItemRequest).await()
      } catch (e: CompletionException) {
        throw e.cause ?: e
      }
    } catch (e: ConditionalCheckFailedException) {
      throw EntityNotFoundException("Product ${product.id.toString()} not found")
    }
  }
}