package com.tradeix.concord.shared.mapper

abstract class Mapper<TSource, TTarget> {

    abstract fun map(source: TSource): TTarget

    fun mapMany(source: Iterable<TSource>): Iterable<TTarget> {
        return source.map { map(it) }
    }
}