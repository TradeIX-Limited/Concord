package com.tradeix.concord.shared.validation

abstract class ObjectValidator<TObject> : Validator() {

    override fun addValidationMessage(validationMessage: String) {
        validationMessages.add(validationMessage)
    }

    override fun getValidationMessages(): Iterable<String> {
        emulating = true
        validateInternal(ValidationContext(this, emulating, null), null)
        return validationMessages
    }

    fun validate(obj: TObject) {
        emulating = false
        validateInternal(ValidationContext(this, emulating, null), obj)
        if (validationMessages.isNotEmpty()) {
            throw ValidationException(validationMessages)
        }
    }

    internal fun validateInternal(context: ValidationContext, obj: TObject?) {
        validationMessages.clear()
        validate(ValidationBuilder(context, obj))
    }

    protected abstract fun validate(validationBuilder: ValidationBuilder<TObject>)
}