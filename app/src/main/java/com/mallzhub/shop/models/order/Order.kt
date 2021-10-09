package com.mallzhub.shop.models.order

import com.mallzhub.shop.models.add_product.AddProductResponseData
import com.mallzhub.shop.models.customers.Customer

data class OrderListResponse(val code: String?, val status: String?, val message: String?, val data: OrderListData?)

data class OrderListData(val sales: Sales?, val total: Total?)

data class Sales(val current_page: Int?, val data: List<SalesData>?, val first_page_url: String?,
                 val from: Int?, val last_page: Int?, val last_page_url: String?,
                 val next_page_url: String?, val path: String?, val per_page: Int?,
                 val prev_page_url: String?, val to: Int?, val total: Int?)

data class Total(val amount: Int?, val vat: Int?, val discount: Int?, val total: Double?)

data class SalesData(val id: Int?, val customer_id: Int?, val merchant_id: Int?, val date: String?,
                     val status: String?, val YourReference: String?, val OurReference: String?,
                     val amount_are: String?, val customer_note: String?, val tax_type_total: Double?,
                     val discount_type_total: Double?, val sub_total: Int?, val grand_total: Double?,
                     val paid_amount: Int?, val due_amount: Double?, val created_at: String?, val updated_at: String?,
                     val details: List<SalesDetails>?, val customer: Customer?)

data class SalesDetails(val id: Int?, val sale_id: Int?, val product_id: Int?, val description: String?,
                        val unitType: String?, val qty: Int?, val unit_price: Int?, val taxType: String?,
                        val taxTypeValue: Double?, val discountType: String?, val discountTypeValue: Double?,
                        val sub_total: Int?, val created_at: String?, val updated_at: String?, val product: AddProductResponseData?)




