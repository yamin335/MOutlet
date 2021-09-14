package com.rtchubs.edokanpat.models.customers
import com.rtchubs.edokanpat.models.login.Merchant

data class CustomerListResponse(val code: String?, val status: String?, val message: String?, val data: CustomerListResponseData?)

data class CustomerListResponseData(val customer: CustomerListData?, val merchant: Merchant?)

data class CustomerListData(val current_page: Int?, val data: List<Customer>?, val first_page_url: String?,
                            val from: Int?, val last_page: Int?, val last_page_url: String?,
                            val next_page_url: String?, val path: String?, val per_page: Int?,
                            val prev_page_url: String?, val to: Int?, val total: Int?)

data class Customer(val id: Int?, val name: String?, val address: String?, val city: String?,
                    val state: String?, val zipcode: String?, val phone: String?,
                    val password: String?, val email: String?, val discountAmount: Int?,
                    val contact_person: String?, val merchant_id: Int?, val created_at: String?,
                    val updated_at: String?)

data class AddCustomerResponse(val code: String?, val status: String?, val message: String?, val data: AddCustomerResponseData?)
data class AddCustomerResponseData(val customer: Customer?)