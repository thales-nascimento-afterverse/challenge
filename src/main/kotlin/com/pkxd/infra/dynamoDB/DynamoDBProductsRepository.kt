package com.pkxd.infra.dynamoDB

import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.domain.model.Product
import com.typesafe.config.Config
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

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
}