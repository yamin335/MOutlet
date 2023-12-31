package com.mallzhub.shop.models.registration

data class InquiryResponse(val body: InquiryBody?, val errorMessage: String?, val timeGenerated: String?, val isSuccess: Boolean?)

data class InquiryBody(val message: String?, val isRegistered: Boolean?, val isAllowed: Boolean?)