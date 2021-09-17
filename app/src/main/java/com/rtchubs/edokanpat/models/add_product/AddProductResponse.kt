package com.rtchubs.edokanpat.models.add_product

data class AddProductResponse(val data: AddProductResponseData?)

data class AddProductResponseData(val name: String?, val barcode: String?,
                val description: String?, val buying_price: String?,
                val selling_price: String?, val mrp: String?, val expired_date: String?,
                val category_id: String?, val merchant_id: String?, val updated_at: String?,
                val created_at: String?, val id: Int?)