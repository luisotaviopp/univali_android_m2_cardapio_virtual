package com.luisZimermann.cardapioVirtualM2.model

data class ProductModel(
    var product_id: Int = 0,
    var amount: Float = 0f,
    var category_name: String = "",
    var description: String = "",
    var product_name: String,
    var thumb_url: String = ""
)