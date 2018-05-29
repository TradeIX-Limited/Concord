package com.tradeix.concord.tools.postman.model

import com.google.gson.annotations.SerializedName

class FormDataRequestBody(@SerializedName("formdata") val formData: Array<FormData>) : RequestBody("formdata") {
    companion object {
        val ATTACHMENT = FormDataRequestBody(arrayOf(FormData("attachment", "", "file")))
    }
}