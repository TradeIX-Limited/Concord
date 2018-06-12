package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

object Mapper {

    @PublishedApi
    internal val standardMapperConfigurations =
            mutableMapOf<MapperConfigurationKey, MapperConfiguration<*, *>>()

    @PublishedApi
    internal val serviceHubMapperConfigurations =
            mutableMapOf<MapperConfigurationKey, ServiceHubMapperConfiguration<*, *>>()

    inline fun <reified TSource, reified TTarget> hasStandardMapperConfiguration(context: String): Boolean {
        return standardMapperConfigurations
                .containsKey(MapperConfigurationKey(context, TSource::class.java, TTarget::class.java))
    }

    inline fun <reified TSource, reified TTarget> hasServiceHubMapperConfiguration(context: String): Boolean {
        return serviceHubMapperConfigurations
                .containsKey(MapperConfigurationKey(context, TSource::class.java, TTarget::class.java))
    }

    inline fun <reified TSource, reified TTarget> addConfiguration(
            context: String,
            configuration: MapperConfiguration<TSource, TTarget>) {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if (hasStandardMapperConfiguration<TSource, TTarget>(context)) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' already exists.")
        }

        standardMapperConfigurations[MapperConfigurationKey(context, sourceClass, targetClass)] = configuration
    }

    inline fun <reified TSource, reified TTarget> addConfiguration(
            context: String,
            configuration: ServiceHubMapperConfiguration<TSource, TTarget>) {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if (hasServiceHubMapperConfiguration<TSource, TTarget>(context)) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' already exists.")
        }

        serviceHubMapperConfigurations[MapperConfigurationKey(context, sourceClass, targetClass)] = configuration
    }

    inline fun <reified TSource, reified TTarget> map(
            context: String,
            source: TSource): TTarget {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        val key = MapperConfigurationKey(context, sourceClass, targetClass)

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

        val key = MapperConfigurationKey(context, sourceClass, targetClass)

        return if (!serviceHubMapperConfigurations.containsKey(key)) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' doesn't exist.")
        } else {
            @Suppress("UNCHECKED_CAST")
            (serviceHubMapperConfigurations[key] as ServiceHubMapperConfiguration<TSource, TTarget>)
                    .map(source, serviceHub)
        }
    }

    inline fun <reified TSource, reified TTarget> mapMany(
            context: String,
            source: Iterable<TSource>): Iterable<TTarget> {
        return source.map { map<TSource, TTarget>(context, it) }
    }

    inline fun <reified TSource, reified TTarget> mapMany(
            context: String,
            source: Iterable<TSource>,
            serviceHub: ServiceHub): Iterable<TTarget> {
        return source.map { map<TSource, TTarget>(context, it, serviceHub) }
    }
}