package com.tradeix.concord.shared.extensions

fun <T> Iterable<T>.isEmpty(): Boolean {
    return this.count() == 0
}

fun <T> Iterable<T>.isNotEmpty(): Boolean {
    return this.count() != 0
}

fun <T> Iterable<T>.containsAll(elements: Iterable<T>): Boolean {
    elements.forEach {
        if (!this.contains(it)) {
            return false
        }
    }

    return true
}