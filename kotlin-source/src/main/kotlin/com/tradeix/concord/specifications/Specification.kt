package com.tradeix.concord.specifications

import java.util.function.Predicate

abstract class Specification<T> {
    companion object {
        fun <T> empty(): Specification<T> = EmptySpecification()
    }

    abstract fun toPredicate(): Predicate<T>
    fun isSatisfiedBy(value: T): Boolean = toPredicate().test(value)
    fun and(other: Specification<T>): Specification<T> = AndSpecification(this, other)
    fun or(other: Specification<T>): Specification<T> = OrSpecification(this, other)
    fun negate(): Specification<T> = NotSpecification(this)
}

internal class AndSpecification<T>(
        private val left: Specification<T>,
        private val right: Specification<T>): Specification<T>() {
    override fun toPredicate(): Predicate<T> = left.toPredicate().and(right.toPredicate())
}

internal class OrSpecification<T>(
        private val left: Specification<T>,
        private val right: Specification<T>) : Specification<T>() {
    override fun toPredicate(): Predicate<T> = left.toPredicate().or(right.toPredicate())
}

internal class NotSpecification<T>(
        private val spec: Specification<T>): Specification<T>() {
    override fun toPredicate(): Predicate<T> = spec.toPredicate().negate()
}

internal class EmptySpecification<T>: Specification<T>() {
    override fun toPredicate(): Predicate<T> = Predicate { true }
}