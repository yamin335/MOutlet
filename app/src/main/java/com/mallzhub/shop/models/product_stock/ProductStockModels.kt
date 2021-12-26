package com.mallzhub.shop.models.product_stock

import com.mallzhub.shop.models.Attribute
import com.mallzhub.shop.models.Product

data class StockProductsResponse(val code: String?, val status: String?, val message: String?, val data: StockProductsResponseData?)

data class StockProductsResponseData(val productDetails: StockProductDetailsData?, val stockwithdetails: StockWithDetails?)

data class StockProductDetailsData(val current_page: Int?, val data: List<StockProductData>?,
                          val first_page_url: String?, val from: Int?, val last_page: Int?,
                          val last_page_url: String?, val next_page_url: String?, val path: String?,
                          val per_page: Int?, val prev_page_url: String?, val to: Int?, val total: Int?)

data class StockProductData(val id: Int?, val product_id: Int?, val name: String?, val qty: Int?, val mrp: Int?,
                            val buying_price: Int?, val selling_price: Int?, val lot: String?, val branch_id: Int?,
                            val warehouse_id: Any?, val expire_date: String?, val created_at: String?,
                            val updated_at: String?, val merchant_id: Int?, val is_opening_stock: Int?,
                            val product: Product?, val stock_barcode: List<StockBarcode>?)

data class StockBarcode(val id: Int?, val product_id: Int?, val product_detail_id: Int?,
                        val merchant_id: Int?, val status: Int?, val salse_date: String?,
                        val receive_date: String?, val barcode: String?, val created_at: String?,
                        val updated_at: String?, val image: String?, val selling_price: Int?,
                        val buying_price: Int?, val attributes: List<Attribute>?)

data class StockWithDetails(val current_page: Int?, val data: List<StockProductWithDetails>?, val first_page_url: String?,
                            val from: Int?, val last_page: Int?, val last_page_url: String?,
                            val next_page_url: String?, val path: String?, val per_page: Int?,
                            val prev_page_url: String?, val to: Int?, val total: Int?)

data class StockProductWithDetails(val product: Product?, val details: List<StockProductDetail>?, val product_id: Int?, val qty: Int?, var isExpanded: Boolean = false)

data class StockProductDetail(val lot: String?, val qty: Int?, val is_opening_stock: Int?, val id: Int?, val created_at: String?)

data class StockProductsDetails(val id: Int?, val product_id: Int?, val product_detail_id: Int?, 
                          val merchant_id: Int?, val status: Int?, val salse_date: String?,
                          val receive_date: String?, val barcode: String?, val created_at: String?, 
                          val updated_at: String?, val image: String?, val selling_price: Int?,
                          val buying_price: Int?, val attributes: List<Attribute>?)