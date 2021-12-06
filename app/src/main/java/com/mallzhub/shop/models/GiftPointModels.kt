package com.mallzhub.shop.models

import java.io.Serializable

data class GiftPointHistoryResponse(val code: Int?, val message: String?, val data: GiftPointHistoryResponseData?)

data class GiftPointHistoryResponseData(val rewards: List<GiftPointRewards>?, val total_reward: Int?)

data class GiftPointRewards(val id: Int?, val created_at: String?, val updated_at: String?,
                            val customer_id: Int?, val merchant_id: Int?, val reward_setting_id: Any?,
                            val customer_mobile: String?, val reward: Int?, val remarks: String?,
                            val is_approved: Int?, val name: String?, val address: String?, 
                            val city: String?, val state: Any?, val zipcode: String?, val phone: String?,
                            val password: String?, val email: String?, val discountAmount: Int?, 
                            val contact_person: String?, val total_rewards: Int?): Serializable