package com.tradeix.concord.shared.validation

import kotlin.reflect.KProperty1

open class ValidationBuilder<TModel>(protected val validator: Validator, protected val receiver: TModel?) {

    fun <TValue> property(property: KProperty1<TModel, TValue>): PropertyValidator<TValue> {
        return PropertyValidator(validator, property.name, if(receiver == null) null else property.get(receiver))
    }

    fun <TValue> validationBuilderFor(selector: (TModel) -> TValue?): ValidationBuilder<TValue> {
        return ValidationBuilder(validator, select(selector))
    }

    fun <TValue> select(selector: (TModel) -> TValue): TValue? {
        return try {
            if(receiver == null) null else selector(receiver)
        } catch (ex: Exception) {
            validator.addValidationMessage(ex.message ?: "Failed to select the requested item from the model.")
            return null
        }
    }

}