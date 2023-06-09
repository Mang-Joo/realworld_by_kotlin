package com.github.io.mangjoo.realworld.config

import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.util.concurrent.atomic.AtomicReference

class DynamicRootValueObjectMapper(builder: Jackson2ObjectMapperBuilder) : ObjectMapper(builder.build<ObjectMapper>()) {

    override fun canSerialize(type: Class<*>, cause: AtomicReference<Throwable>): Boolean {
        enableOrDisableRootValueByAnnotation(type)
        return super.canSerialize(type, cause)
    }

    override fun canDeserialize(type: JavaType, cause: AtomicReference<Throwable>?): Boolean {
        enableOrDisableRootValueByAnnotation(type.rawClass)
        return super.canDeserialize(type, cause)
    }

    override fun writeValueAsString(value: Any): String {
        enableOrDisableRootValueByAnnotation(value.javaClass)
        this.registerModule(JavaTimeModule())
        this.registerKotlinModule()
        return super.writeValueAsString(value)
    }

    private fun enableOrDisableRootValueByAnnotation(valueType: Class<*>) {
        if (valueType.isAnnotationPresent(JsonRootName::class.java)) {
            enable(SerializationFeature.WRAP_ROOT_VALUE)
            enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
        } else {
            disable(SerializationFeature.WRAP_ROOT_VALUE)
            disable(DeserializationFeature.UNWRAP_ROOT_VALUE)
        }
    }
}