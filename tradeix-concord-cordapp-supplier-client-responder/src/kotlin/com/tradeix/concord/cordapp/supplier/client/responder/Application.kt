package com.tradeix.concord.cordapp.supplier.client.responder

import com.tradeix.concord.shared.client.http.HttpClient

fun main(args: Array<String>) {
    val client = HttpClient("http://localhost:5000/")
    client.post("Invoice/Notify", "{ \"externalId\": \"INVOICE_123\", \"transactionId\": \"079b2b1e14930fd90a5858354283ed3b7cbc0c78394232c2f785d82c7a21a78e\" }")
}