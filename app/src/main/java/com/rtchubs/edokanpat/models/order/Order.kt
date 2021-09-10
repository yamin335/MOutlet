package com.rtchubs.edokanpat.models.order

data class Order(val id: Int, val invoiceId: String, val dateTime: String, val status: String)
