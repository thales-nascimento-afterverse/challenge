package com.pkxd.infra.dynamoDB

import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.model.Profile
import com.pkxd.model.Wallet
import com.typesafe.config.Config
import java.util.UUID
import kotlinx.coroutines.future.await
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

class DynamoDBProfileRepository(config: Config, private val dynamoDbClient: DynamoDbAsyncClient) : ProfileRepository {
  private val tableName = config.getString("dynamoDB.profile.table-name")
  override suspend fun add(profile: Profile) {
    val walletItem = mutableMapOf<String, AttributeValue>()
    walletItem["coins"] = AttributeValue.builder().n(profile.wallet.coins.toString()).build()
    walletItem["gems"] = AttributeValue.builder().n(profile.wallet.gems.toString()).build()

    val profileItem = mutableMapOf<String, AttributeValue>()
    profileItem["id"] = AttributeValue.builder().s(profile.id.toString()).build()
    profileItem["nickname"] = AttributeValue.builder().s(profile.nickname).build()
    profileItem["email"] = AttributeValue.builder().s(profile.email).build()
    profileItem["wallet"] = AttributeValue.builder().m(walletItem).build()

    val putItemRequest = PutItemRequest.builder().tableName(tableName).item(profileItem).build()
    dynamoDbClient.putItem(putItemRequest)
  }

  override suspend fun findById(id: UUID): Profile? {
    try {
      val key = mutableMapOf<String, AttributeValue>()
      key["id"] = AttributeValue.builder().s(id.toString()).build()
      val getItemRequest = GetItemRequest.builder()
        .tableName(tableName)
        .key(key)
        .build()

      return dynamoDbClient.getItem(getItemRequest).await().item()?.toProfile()
    } catch (e: DynamoDbException) {
      throw e.cause ?: e
    }
  }

  private fun Map<String, AttributeValue>.toProfile(): Profile {
    return Profile(
      id = UUID.fromString(this["id"]?.s().toString()),
      nickname = this["nickname"]?.s().toString(),
      email = this["email"]?.s().toString(),
      wallet = Wallet(
        coins = this["wallet"]?.m()?.get("coins")?.n()?.toInt() ?: 0,
        gems = this["wallet"]?.m()?.get("gems")?.n()?.toInt() ?: 0
      )
    )
  }
}