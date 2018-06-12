package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

abstract class ServiceHubMapper<TSource, TTarget> {

    abstract fun map(source: TSource, serviceHub: ServiceHub): TTarget

    fun mapMany(source: Iterable<TSource>, serviceHub: ServiceHub): Iterable<TTarget> {
        return source.map { map(it, serviceHub) }
    }
}