package com.pkxd

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.pkxd.main.config.*

class ApplicationTest {
    @Test
    fun testRoot() {
        /*withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }*/
    }
}