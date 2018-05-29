package com.tradeix.concord.shared.validation

abstract class ObjectModelValidator<TModel> : Validator(ValidationBehavior.ADD_MESSAGE_ON_ERROR) {

    override fun getValidationMessages(): Iterable<String> {
        initializeValidation(false)
        return validationMessages
    }

    fun validate(model: TModel) {
        initializeValidation(true, model)
        if (validationMessages.isNotEmpty()) {
            throw ValidationException(validationMessages)
        }
    }

    protected abstract fun onValidationBuilding(validationBuilder: ValidationBuilder<TModel>)

    private fun initializeValidation(validating: Boolean, model: TModel? = null) {
        validationMessages.clear()
        this.validating = validating
        onValidationBuilding(ValidationBuilder(this, model))
    }
}