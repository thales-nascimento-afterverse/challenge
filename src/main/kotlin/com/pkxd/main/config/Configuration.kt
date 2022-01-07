package com.pkxd.main.config

import com.pkxd.data.usecases.product.CreateProductUseCase
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
    DynamoDBProfileRepository(config = config, dynamoDbClient = AWSConfiguration.dynamoDBClient)
  private val dynamoDBProductsRepository =
    DynamoDBProductsRepository(config = config, dynamoDbClient = AWSConfiguration.dynamoDBClient)

  val createProfileUseCase: CreateProfileUseCase =
    CreateProfileUseCase(profileRepository = dynamoDBProfileRepository, idGenerator = uuiGenerator)
  val createProductUseCase: CreateProductUseCase =
    CreateProductUseCase(productRepository = dynamoDBProductsRepository, idGenerator = uuiGenerator)
}