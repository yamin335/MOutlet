package com.rtchubs.edokanpat.models.add_product

import android.graphics.Bitmap

data class ItemAddProduct(val id: Int?, var name: String?, var price: Int?,
                          var category: String?, var description: String?,
                          var featureImage: Bitmap?, var sampleImages: List<Bitmap>)
