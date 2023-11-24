package com.theroyalsoft.telefarmer.model.loan

data class LoanDetailsResponseItem(
    val crop_name: String,
    val crop_cost: List<LoanCostPer>
)