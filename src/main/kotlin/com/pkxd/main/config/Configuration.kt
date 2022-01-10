package com.pkxd.main.config

import com.pkxd.data.usecases.product.CreateProductUseCase
import com.pkxd.data.usecases.product.DeleteProductUseCase
import com.pkxd.data.usecases.product.UpdateProductUseCase
import com.pkxd.data.usecases.profile.CreateProfileUseCase
import com.pkxd.infra.dynamoDB.DynamoDBProductsRepository
import com.pkxd.infra.dynamoDB.DynamoDBProfileRepository
import com.pkxd.infra.uuid.UUIDGenerator
import com.typesafe.config.ConfigFactory

object Configuration {
  private val config = System.getenv()["ENVIRONMENT_MODE"].let {
    ConfigFactory.load(it)
  }
  private val uuiGenerator = UUIDGenerator
  private val dynamoDBProfileRepository =
    DynamoDBProfileRepository(config, dynamoDbClient = AWSConfiguration.dynamoDBClient)
  private val dynamoDBProductsRepository =
    DynamoDBProductsRepository(config, dynamoDbClient = AWSConfiguration.dynamoDBClient)

  val createProfileUseCase = CreateProfileUseCase(dynamoDBProfileRepository, uuiGenerator)
  val createProductUseCase = CreateProductUseCase(dynamoDBProductsRepository, uuiGenerator)
  val deleteProductUseCase = DeleteProductUseCase(dynamoDBProductsRepository)
  val updateProductUseCase = UpdateProductUseCase(dynamoDBProductsRepository)
}