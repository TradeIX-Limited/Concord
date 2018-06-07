package com.tradeix.concord.shared.validation

import kotlin.reflect.KProperty1

open class ValidationBuilder<TObject>(private val context: ValidationContext, private val obj: TObject?) {

    fun <TProperty> property(
            property: KProperty1<TObject, TProperty>,
            function: (PropertyValidator<TProperty>) -> Unit) {
        function(PropertyValidator(
                context.withMemberAccessor(property.name),
                getScalarValueOrNull(property))
        )
    }

    fun <TProperty> property(
            property: KProperty1<TObject, TProperty>,
            validator: ObjectValidator<TProperty>) {
        validator.validateInternal(
                context.withMemberAccessor(property.name),
                getScalarValueOrNull(property)
        )
    }

    fun <TProperty> collection(
            property: KProperty1<TObject, Iterable<TProperty>?>,
            function: (PropertyValidator<TProperty?>) -> Unit) {
        getIterableValueOrSingle(property).forEach {
            function(PropertyValidator(context.withIndexAccessor(property.name), it))
        }
    }

    fun <TProperty> collection(
            property: KProperty1<TObject, Iterable<TProperty>?>,
            validator: ObjectValidator<TProperty>) {
        getIterableValueOrSingle(property).forEach {
            validator.validateInternal(context.withIndexAccessor(property.name), it)
        }
    }

    fun validateWith(validationMessage: String, validationFunction: (TObject) -> Boolean) {
        val result = obj?.let(validationFunction) ?: false
        if (!result) {
            context.validator.addValidationMessage(validationMessage)
        }
    }

    private fun <TProperty> getScalarValueOrNull(
            property: KProperty1<TObject, TProperty>): TProperty? {
        return if (obj != null) property.get(obj) else null
    }

    private fun <TProperty> getIterableValueOrSingle(
            property: KProperty1<TObject, Iterable<TProperty>?>): Iterable<TProperty?> {
        return if (obj != null) property.get(obj) as Iterable<TProperty?> else listOf(null)
    }
}