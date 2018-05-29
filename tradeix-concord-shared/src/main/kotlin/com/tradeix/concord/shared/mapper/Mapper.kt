package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

typealias Key = Triple<String, Class<*>, Class<*>>

object Mapper {

    @PublishedApi
    internal val standardMapperConfigurations =
            mutableMapOf<Key, MapperConfiguration<*, *>>()

    @PublishedApi
    internal val serviceHubMapperConfigurations =
            mutableMapOf<Key, ServiceHubMapperConfiguration<*, *>>()

    inline fun <reified TSource, reified TTarget> hasStandardMapperConfiguration(context: String): Boolean {
        return standardMapperConfigurations
                .containsKey(Key(context, TSource::class.java, TTarget::class.java))
    }

    inline fun <reified TSource, reified TTarget> hasServiceHubMapperConfiguration(context: String): Boolean {
        return serviceHubMapperConfigurations
                .containsKey(Key(context, TSource::class.java, TTarget::class.java))
    }


    inline fun <reified TSource, reified TTarget> addConfiguration(
            context: String,
            configuration: MapperConfiguration<TSource, TTarget>) {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if (hasStandardMapperConfiguration<TSource, TTarget>(context)) {
            // TODO : review logic here...
            // throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' already exists.")
            return
        }

        standardMapperConfigurations[Key(context, sourceClass, targetClass)] = configuration
    }

    inline fun <reified TSource, reified TTarget> addConfiguration(
            context: String,
            configuration: ServiceHubMapperConfiguration<TSource, TTarget>) {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if (hasServiceHubMapperConfiguration<TSource, TTarget>(context)) {
            // TODO : review logic here...
            // throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' already exists.")
            return
        }

        serviceHubMapperConfigurations[Key(context, sourceClass, targetClass)] = configuration
    }

    inline fun <reified TSource, reified TTarget> map(
            context: String,
            source: TSource): TTarget {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        val key = Key(context, sourceClass, targetClass)

        return if (!standardMapperConfigurations.containsKey(key)) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' doesn't exist.")
        } else {
            @Suppress("UNCHECKED_CAST")
            (standardMapperConfigurations[key] as MapperConfiguration<TSource, TTarget>)
                    .map(source)
        }
    }

    inline fun <reified TSource, reified TTarget> map(
            context: String,
            source: TSource,
            serviceHub: ServiceHub): TTarget {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        val key = Key(context, sourceClass, targetClass)

        return if (!serviceHubMapperConfigurations.containsKey(key)) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' doesn't exist.")
        } else {
            @Suppress("UNCHECKED_CAST")
            (serviceHubMapperConfigurations[key] as ServiceHubMapperConfiguration<TSource, TTarget>)
                    .map(source, serviceHub)
        }
    }
}