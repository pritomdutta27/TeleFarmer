package com.farmer.primary.network.model.home

data class NewsModel(
    val dateAndTime: String,
    val details: String,
    val imageUrl: String,
    val isHighlighted: Boolean,
    val title: String
)