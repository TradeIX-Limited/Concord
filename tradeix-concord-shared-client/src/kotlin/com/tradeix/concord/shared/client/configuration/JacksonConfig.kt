//package com.tradeix.concord.shared.client.configuration
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.SerializationFeature
//import com.fasterxml.jackson.databind.util.ISO8601DateFormat
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Primary
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
//
//@Configuration
//class JacksonConfig {
//
//    @Bean
//    @Primary
//    fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
//        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//        objectMapper.dateFormat = ISO8601DateFormat()
//        return objectMapper
//    }
//}