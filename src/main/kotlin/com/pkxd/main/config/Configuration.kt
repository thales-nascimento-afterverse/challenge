package com.pkxd.main.config

import com.pkxd.data.usecases.profile.CreateProfileUseCase
import com.pkxd.infra.dynamoDB.DynamoDBRepository
import com.pkxd.infra.uuid.UUIDGenerator

object Configuration {
  private val dynamoDBRepository = DynamoDBRepository(AWSConfiguration.dynamoDBClient)
  private val uuiGenerator = UUIDGenerator
  val createProfileUseCase: CreateProfileUseCase = CreateProfileUseCase(dynamoDBRepository, uuiGenerator)
}