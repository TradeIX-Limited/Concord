package com.tradeix.concord.shared.mapper

abstract class MapperConfiguration<TSource, TTarget> {
    abstract fun map(source: TSource): TTarget
}