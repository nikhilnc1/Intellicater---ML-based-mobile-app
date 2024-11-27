package com.example.waveoffood.Model

data class CartItems(
    var menuKey: String ?= null,
    var foodName: String ?=null,
    var foodPrice: String ?=null,
    var foodDescription: String ?=null,
    var foodQuantity: Int ?=null,
    var foodIngredients:String?= null
)
