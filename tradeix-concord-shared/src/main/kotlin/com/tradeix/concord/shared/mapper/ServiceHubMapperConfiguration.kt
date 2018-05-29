package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

abstract class ServiceHubMapperConfiguration<TSource, TTarget> {
    abstract fun map(source: TSource, serviceHub: ServiceHub): TTarget
}