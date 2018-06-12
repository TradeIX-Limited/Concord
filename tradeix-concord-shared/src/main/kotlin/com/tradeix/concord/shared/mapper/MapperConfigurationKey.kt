package com.tradeix.concord.shared.mapper

data class MapperConfigurationKey(
        val context: String,
        val source: Class<*>,
        val target: Class<*>
)