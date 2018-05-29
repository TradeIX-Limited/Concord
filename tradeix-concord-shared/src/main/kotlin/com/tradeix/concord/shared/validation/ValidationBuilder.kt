package com.tradeix.concord.shared.validation

import kotlin.reflect.KProperty1

open class ValidationBuilder<TModel>(protected val validator: Validator, protected val receiver: TModel?) {

    fun <TValue> property(property: KProperty1<TModel, TValue>): PropertyValidator<TValue> {
        return PropertyValidator(validator, property.name, if(receiver == null) null else property.get(receiver))
    }
}