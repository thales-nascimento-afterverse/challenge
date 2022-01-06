package com.pkxd.utils.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author José Carlos Cieni Júnior (cieni@playkids.com)
 */
fun logger(clazz: Class<*>): Logger = LoggerFactory.getLogger(clazz)
inline fun <reified T : Any> logger() = logger(T::class.java)

fun logger(name: String): Logger = LoggerFactory.getLogger(name)