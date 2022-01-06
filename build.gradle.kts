val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val slf4jOverLog4jVersion: String by project

plugins {
  application
  kotlin("jvm") version "1.6.0"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
}

group = "com.pkxd"
version = "0.0.1"
application {
  mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform("software.amazon.awssdk:bom:2.17.1"))
  implementation("software.amazon.awssdk:dynamodb")

  implementation("ch.qos.logback:logback-classic:$logback_version")

  implementation("io.ktor:ktor-server-core:$ktor_version")
  implementation("io.ktor:ktor-serialization:$ktor_version")
  implementation("io.ktor:ktor-server-netty:$ktor_version")
  testImplementation("io.ktor:ktor-server-tests:$ktor_version")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}