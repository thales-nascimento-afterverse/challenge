package com.pkxd.main.config

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import com.pkxd.data.usecases.item.CreateItemUseCase
import com.pkxd.data.usecases.item.DeleteItemUseCase
import com.pkxd.data.usecases.item.UpdateItemUseCase
import com.pkxd.data.usecases.product.CreateProductUseCase
import com.pkxd.data.usecases.product.DeleteProductUseCase
import com.pkxd.data.usecases.product.UpdateProductUseCase
import com.pkxd.data.usecases.profile.CreateProfileUseCase
import com.pkxd.data.usecases.profile.FindProfileByIdUseCase
import com.pkxd.data.usecases.store.PurchaseProductsUseCase
import com.pkxd.infra.dynamoDB.DynamoDBItemRepository
import com.pkxd.infra.dynamoDB.DynamoDBProductsRepository
import com.pkxd.infra.dynamoDB.DynamoDBProfileRepository
import com.typesafe.config.ConfigFactory
import java.util.UUID

object Configuration {
  private val config = System.getenv()["ENVIRONMENT_MODE"].let {
    ConfigFactory.load(it)
  }
  private val uuidGenerator = IdGenerator<UUID>(UUID::randomUUID)
  private val dynamoDBProfileRepository =
    DynamoDBProfileRepository(config, AWSConfiguration.dynamoDBClient)
  private val dynamoDBProductsRepository =
    DynamoDBProductsRepository(config, AWSConfiguration.dynamoDBClient)
  private val dynamoDBItemRepository =
    DynamoDBItemRepository(config, AWSConfiguration.dynamoDBClient)

  val createProfileUseCase = CreateProfileUseCase(dynamoDBProfileRepository, uuidGenerator)
  val findProfileByIdUseCase = FindProfileByIdUseCase(dynamoDBProfileRepository)
  val createProductUseCase = CreateProductUseCase(dynamoDBProductsRepository, uuidGenerator)
  val deleteProductUseCase = DeleteProductUseCase(dynamoDBProductsRepository)
  val updateProductUseCase = UpdateProductUseCase(dynamoDBProductsRepository)
  val createItemUseCase = CreateItemUseCase(dynamoDBItemRepository, uuidGenerator)
  val deleteItemUseCase = DeleteItemUseCase(dynamoDBItemRepository)
  val updateItemUseCase = UpdateItemUseCase(dynamoDBItemRepository)
  val purchaseProductsUseCase = PurchaseProductsUseCase(dynamoDBProfileRepository, dynamoDBProductsRepository)
}