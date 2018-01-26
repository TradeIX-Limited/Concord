package com.tradeix.concord.extensions

object ArrayListExtensions {
    inline fun <reified T> ArrayList<T>.addWhen(condition: Boolean, element: T) {
        if (condition) this.add(element)
    }
}