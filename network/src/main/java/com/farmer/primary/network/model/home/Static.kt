package com.farmer.primary.network.model.home

data class Static(
    val news: List<NewsModel>,
    val tips_categories: List<TipsCategory>,
    val tricks_tips: List<TricksTip>
)