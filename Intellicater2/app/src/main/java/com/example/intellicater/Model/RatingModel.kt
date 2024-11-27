package com.example.intellicater.Model

class RatingModel(
    var menuKey: String? = null,
    var foodName: String? = null,
    var rating: Int = 0,
    var userUid: String? = null
) {
    constructor() : this("", "", 0, "")
}
