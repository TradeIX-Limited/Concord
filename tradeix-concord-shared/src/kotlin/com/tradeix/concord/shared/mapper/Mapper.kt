package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

object Mapper {

    @PublishedApi
    internal val configurations: MutableMap<Triple<String, Class<*>, Class<*>>, MapperConfiguration<*, *>> = mutableMapOf()

    inline fun <reified TSource, reified TTarget> addConfiguration(
            context: String,
            mapperConfiguration: MapperConfiguration<TSource, TTarget>) {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if(configurations.containsKey(Triple(context, sourceClass, targetClass))) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' already exists.")
        }

        configurations[Triple(context, sourceClass, targetClass)] = mapperConfiguration
    }

    inline fun <reified TSource, reified TTarget> map(
            context: String,
            source: TSource,
            serviceHub: ServiceHub): TTarget {

        val sourceClass = TSource::class.java
        val targetClass = TTarget::class.java

        if(!configurations.containsKey(Triple(context, sourceClass, targetClass))) {
            throw MapperException("Mapping from '${sourceClass.name}' to '${targetClass.name}' doesn't exist.")
        }

        val configuration = configurations[Triple(context, sourceClass, targetClass)]!!

        @Suppress("UNCHECKED_CAST")
        return (configuration as MapperConfiguration<TSource, TTarget>).map(source, serviceHub)
    }
}