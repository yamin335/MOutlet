package com.mallzhub.shop.local_db.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mallzhub.shop.models.Attribute
import com.mallzhub.shop.models.OrderProduct
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.ProductCategory
import java.lang.reflect.Type

class RoomDataConverter {
    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun jsonStringToCategory(value: String?): ProductCategory? {
        return gson.fromJson(value, ProductCategory::class.java)
    }

    @TypeConverter
    fun categoryToJsonString(category: ProductCategory?): String? {
        return gson.toJson(category)
    }

    @TypeConverter
    fun jsonStringToProduct(value: String): Product {
        return gson.fromJson(value, Product::class.java)
    }

    @TypeConverter
    fun productToJsonString(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun jsonStringToOrderProduct(value: String): OrderProduct {
        return gson.fromJson(value, OrderProduct::class.java)
    }

    @TypeConverter
    fun orderProductToJsonString(product: OrderProduct): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun jsonStringToAttribute(value: String): List<Attribute> {
        val listType: Type = object : TypeToken<List<Attribute>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun attributeListToJsonString(attributeList: List<Attribute>): String {
        return gson.toJson(attributeList)
    }

//    @TypeConverter
//    fun fromString(value: String?): ArrayList<String?>? {
//        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
//        return gson.fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromArrayLisr(list: ArrayList<String?>?): String? {
//        return gson.toJson(list)
//    }

}
