package com.tradeix.concord.shared.mapper

import net.corda.core.node.ServiceHub

abstract class MapperConfiguration<TSource, TTarget> {
    abstract fun map(source: TSource, serviceHub: ServiceHub): TTarget
}